/*
 * Copyright (c) 2022, Hongtao Liang (lhongtao.hometown@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htliang.db.mybatisplus.codegen.utils;

import lombok.SneakyThrows;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.table.Index;
import org.springframework.core.io.Resource;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SqlProcessing {
    @SneakyThrows
    public static String getCreateTableSqls(Resource resource) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String rawSqlContent = bufferedReader
                .lines()
                .collect(Collectors.joining("\n"));

            String sqlContent = preprocessingSqlContent(rawSqlContent);
            CCJSqlParser ccjSqlParser = CCJSqlParserUtil.newParser(sqlContent);

            List<CreateTable> createTables = new ArrayList<>();
            ccjSqlParser
                .Statements()
                .getStatements()
                .forEach(statement -> {
                    if (checkIfStatementIsCreateTable(statement)) {
                        createTables.add((CreateTable) statement);
                    }
                });

            for (CreateTable createTable : createTables) {
                removeCharacterSetAndCollate(createTable);
                removePartitions(createTable);
                removeUnionKey(createTable);
            }

            return createTables
                .stream()
                .map(CreateTable::toString)
                .collect(Collectors.joining(";\n"));
        }
    }

    private static String preprocessingSqlContent(String rawSqlContent) {
        return rawSqlContent
            .replaceAll("\\sindex\\s", " KEY ")
            .replaceAll("\\sINDEX\\s", " KEY ");
    }

    private static void removePartitions(CreateTable createTable) {
        removeSeveralStringFromGivingStartString(createTable.getTableOptionsStrings(), "PARTITIONS", 2);
        removeSeveralStringFromGivingStartString(createTable.getTableOptionsStrings(), "partitions", 2);
    }

    private static void removeCharacterSetAndCollate(CreateTable createTable) {
        List<ColumnDefinition> columnDefinitions = createTable.getColumnDefinitions();
        for (ColumnDefinition columnDefinition : columnDefinitions) {
            removeColumnDefinitionCharacterSet(columnDefinition);
            removeColumnDefinitionCollate(columnDefinition.getColumnSpecs());
        }

        List<String> tableOptionsStrings = createTable.getTableOptionsStrings();
        removeTableOptionsCharacterSet(tableOptionsStrings);
        removeTableOptionsCollate(tableOptionsStrings);
    }

    private static void removeUnionKey(CreateTable createTable) {
        List<Index> indices = createTable.getIndexes();
        if (CollectionUtils.isEmpty(indices)) {
            return;
        }

        int primaryKeyIndexOfIndices = getPrimaryKeyIndexOfIndices(indices);
        if (primaryKeyIndexOfIndices < 0) {
            return;
        }

        Index primaryKeyIndex = indices.get(primaryKeyIndexOfIndices);

        if (primaryKeyIndex
            .getColumns()
            .size() == 1) {
            return;
        }

        List<String> tableOptionsStrings = createTable.getTableOptionsStrings();
        int partitionIndexOfOptionsStrings = getPartitionIndexOfOptionsStrings(tableOptionsStrings);
        if (partitionIndexOfOptionsStrings < 0) {
            primaryKeyIndex.setColumns(Collections.singletonList(primaryKeyIndex
                .getColumns()
                .get(0)));
            return;
        }

        String partitionKey = getRawColumn(tableOptionsStrings.get(partitionIndexOfOptionsStrings + 3));
        String firstPrimaryKey = getRawColumn(primaryKeyIndex
            .getColumns()
            .get(0)
            .getColumnName());

        removeSeveralStringsFromGivingStartIndex(tableOptionsStrings, partitionIndexOfOptionsStrings, 5);

        if (partitionKey.equals(firstPrimaryKey)) {
            primaryKeyIndex.setColumns(Collections.singletonList(primaryKeyIndex
                .getColumns()
                .get(1)));
            return;
        }
        primaryKeyIndex.setColumns(Collections.singletonList(primaryKeyIndex
            .getColumns()
            .get(0)));
    }

    private static int getPrimaryKeyIndexOfIndices(List<Index> indices) {
        for (int i = 0; i < indices.size(); i++) {
            if (indices
                .get(i)
                .getType()
                .equalsIgnoreCase("PRIMARY KEY")) {
                return i;
            }
        }
        return -1;
    }

    private static int getPartitionIndexOfOptionsStrings(List<String> optionsStrings) {
        for (int i = 0; i < optionsStrings.size(); i++) {
            if (optionsStrings
                .get(i)
                .equalsIgnoreCase("PARTITION")) {
                return i;
            }
        }
        return -1;
    }

    private static String getRawColumn(String rawColumn) {
        return rawColumn
            .replaceAll("\\(", "")
            .replaceAll("\\)", "")
            .replaceAll("`", "")
            .replaceAll("'", "")
            .replaceAll("\"", "");
    }

    private static void removeColumnDefinitionCharacterSet(ColumnDefinition columnDefinition) {
        columnDefinition
            .getColDataType()
            .setCharacterSet(null);
    }

    private static void removeColumnDefinitionCollate(List<String> columnDefinition) {
        removeSeveralStringFromGivingStartString(columnDefinition, "COLLATE", 2);
        removeSeveralStringFromGivingStartString(columnDefinition, "collate", 2);
    }

    private static void removeTableOptionsCharacterSet(List<String> tableOptions) {
        removeSeveralStringFromGivingStartString(tableOptions, "CHARACTER", 4);
        removeSeveralStringFromGivingStartString(tableOptions, "character", 4);
    }

    private static void removeTableOptionsCollate(List<String> tableOptions) {
        removeSeveralStringFromGivingStartString(tableOptions, "COLLATE", 3);
        removeSeveralStringFromGivingStartString(tableOptions, "collate", 3);
    }

    private static void removeSeveralStringFromGivingStartString(List<String> strings, String startString, int limit) {
        if (!strings.contains(startString)) {
            return;
        }
        int index = strings.indexOf(startString);
        removeSeveralStringsFromGivingStartIndex(strings, index, limit);
    }

    private static void removeSeveralStringsFromGivingStartIndex(List<String> strings, int index, int limit) {
        for (int i = 0; i < limit; i++) {
            strings.remove(index);
        }
    }

    private static boolean checkIfStatementIsCreateTable(Statement statement) {
        return statement
            .toString()
            .startsWith("CREATE TABLE");
    }
}

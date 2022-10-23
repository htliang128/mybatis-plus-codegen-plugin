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

package com.htliang.db.mybatisplus.codegen;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.google.common.base.Strings;
import com.htliang.db.mybatisplus.codegen.option.strategy.BaseStrategy;
import com.htliang.db.mybatisplus.codegen.option.TemplateOption;
import com.htliang.db.mybatisplus.codegen.option.strategy.ControllerStrategy;
import com.htliang.db.mybatisplus.codegen.option.strategy.EntityStrategy;
import com.htliang.db.mybatisplus.codegen.option.GlobalOption;
import com.htliang.db.mybatisplus.codegen.option.strategy.MapperStrategy;
import com.htliang.db.mybatisplus.codegen.option.PackageOption;
import com.htliang.db.mybatisplus.codegen.option.strategy.ServiceStrategy;
import com.htliang.db.mybatisplus.codegen.option.StrategyOption;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class ConfigBuilder {
    @NotNull
    static Consumer<GlobalConfig.Builder> buildGlobalConfig(GlobalOption globalOption) {
        return builder -> {
            if (globalOption.isDisableOpenDir()) {
                builder.disableOpenDir();
            }

            if (globalOption.isEnableKotlin()) {
                builder.enableKotlin();
            }

            if (globalOption.isEnableSwagger()) {
                builder.enableSwagger();
            }

            builder
                .outputDir(globalOption.getOutputDir())
                .author(globalOption.getAuthor())
                .dateType(globalOption.getDateType())
                .commentDate(globalOption.getCommentDate());
        };
    }

    @NotNull
    static Consumer<PackageConfig.Builder> buildPackageConfig(PackageOption packageOption) {
        Map<OutputFile, String> pathInfo = getPathInfo(packageOption);

        return builder -> builder
            .parent(packageOption.getParent())
            .moduleName(packageOption.getModuleName())
            .entity(packageOption.getEntity())
            .service(packageOption.getService())
            .serviceImpl(packageOption.getServiceImpl())
            .mapper(packageOption.getMapper())
            .xml(packageOption.getXml())
            .controller(packageOption.getController())
            .pathInfo(pathInfo);
    }

    @NotNull
    static Consumer<TemplateConfig.Builder> buildTemplateConfig(TemplateOption templateOption) {
        return builder -> {
            disableTemplates(templateOption, builder);
            setCustomTemplate(templateOption, builder);
        };
    }

    private static void disableTemplates(TemplateOption templateOption, TemplateConfig.Builder builder) {
        if (templateOption.isDisableAll()) {
            builder.disable();
        }

        if (templateOption.isDisableController()) {
            builder.disable(TemplateType.CONTROLLER);
        }

        if (templateOption.isDisableEntity()) {
            builder.disable(TemplateType.ENTITY);
        }

        if (templateOption.isDisableService()) {
            builder.disable(TemplateType.SERVICE);
        }

        if (templateOption.isDisableServiceImpl()) {
            builder.disable(TemplateType.SERVICE_IMPL);
        }

        if (templateOption.isDisableMapper()) {
            builder.disable(TemplateType.MAPPER);
        }

        if (templateOption.isDisableXml()) {
            builder.disable(TemplateType.XML);
        }
    }

    private static void setCustomTemplate(TemplateOption templateOption, TemplateConfig.Builder builder) {
        if (!Strings.isNullOrEmpty(templateOption.getEntity())) {
            builder.entity(templateOption.getEntity());
        }

        if (!Strings.isNullOrEmpty(templateOption.getEntityKt())) {
            builder.entityKt(templateOption.getEntityKt());
        }

        if (!Strings.isNullOrEmpty(templateOption.getService())) {
            builder.service(templateOption.getService());
        }

        if (!Strings.isNullOrEmpty(templateOption.getServiceImpl())) {
            builder.serviceImpl(templateOption.getServiceImpl());
        }

        if (!Strings.isNullOrEmpty(templateOption.getMapper())) {
            builder.mapper(templateOption.getMapper());
        }

        if (!Strings.isNullOrEmpty(templateOption.getMapperXml())) {
            builder.xml(templateOption.getMapperXml());
        }

        if (!Strings.isNullOrEmpty(templateOption.getController())) {
            builder.controller(templateOption.getController());
        }
    }

    @NotNull
    static Consumer<StrategyConfig.Builder> buildStrategyConfig(StrategyOption strategyOption) {
        return builder -> {
            buildBaseStrategy(builder, strategyOption.getBaseStrategy());
            buildControllerStrategy(builder, strategyOption.getControllerStrategy());
            buildEntityStrategy(builder, strategyOption.getEntityStrategy());
            buildServiceStrategy(builder, strategyOption.getServiceStrategy());
            buildMapperStrategy(builder, strategyOption.getMapperStrategy());
        };
    }

    static void buildBaseStrategy(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (baseStrategy.isEnableCapitalMode()) {
            builder.enableCapitalMode();
        }

        if (baseStrategy.isEnableSkipView()) {
            builder.enableSkipView();
        }

        if (baseStrategy.isDisableSqlFilter()) {
            builder.disableSqlFilter();
        }

        if (baseStrategy.isEnableSchema()) {
            builder.enableSchema();
        }

        setLikeOrNotLikeTable(builder, baseStrategy);
        addIncludeOrExcludeColumns(builder, baseStrategy);
        addPrefixesOrSuffixes(builder, baseStrategy);
    }

    private static void setLikeOrNotLikeTable(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (!Strings.isNullOrEmpty(baseStrategy.getLikeTableValue())) {
            builder.likeTable(new LikeTable(baseStrategy.getLikeTableValue(), Objects.isNull(baseStrategy.getLikeTableSqlLike()) ?
                SqlLike.DEFAULT :
                baseStrategy.getLikeTableSqlLike()));
        }

        if (!Strings.isNullOrEmpty(baseStrategy.getNotLikeTableValue())) {
            builder.notLikeTable(new LikeTable(baseStrategy.getNotLikeTableValue(), Objects.isNull(baseStrategy.getNotLikeTableSqlLike()) ?
                SqlLike.DEFAULT :
                baseStrategy.getNotLikeTableSqlLike()));
        }
    }

    private static void addIncludeOrExcludeColumns(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getIncludeColumns())) {
            builder.addInclude(baseStrategy.getIncludeColumns());
        }

        if (Objects.nonNull(baseStrategy.getExcludeColumns())) {
            builder.addExclude(baseStrategy.getExcludeColumns());
        }
    }

    private static void addPrefixesOrSuffixes(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getTablePrefixes())) {
            builder.addTablePrefix(baseStrategy.getTablePrefixes());
        }

        if (Objects.nonNull(baseStrategy.getTableSuffixes())) {
            builder.addTableSuffix(baseStrategy.getTableSuffixes());
        }

        if (Objects.nonNull(baseStrategy.getFieldPrefixes())) {
            builder.addFieldPrefix(baseStrategy.getFieldPrefixes());
        }

        if (Objects.nonNull(baseStrategy.getFieldSuffixes())) {
            builder.addFieldSuffix(baseStrategy.getFieldSuffixes());
        }
    }

    static void buildControllerStrategy(StrategyConfig.Builder builder, ControllerStrategy controllerStrategy) {
        Controller.Builder controllerBuilder = builder.controllerBuilder();

        if (controllerStrategy.isEnableFileOverride()) {
            controllerBuilder.enableFileOverride();
        }

        if (controllerStrategy.isEnableHyphenStyle()) {
            controllerBuilder.enableHyphenStyle();
        }

        if (controllerStrategy.isEnableRestStyle()) {
            controllerBuilder.enableRestStyle();
        }

        controllerBuilder
            .superClass(controllerStrategy.getSuperClass())
            .formatFileName(controllerStrategy.getFileNameFormat());
    }

    static void buildEntityStrategy(StrategyConfig.Builder builder, EntityStrategy entityStrategy) {
        Entity.Builder entityBuilder = builder.entityBuilder();

        if (entityStrategy.isEnableFileOverride()) {
            entityBuilder.enableFileOverride();
        }

        if (entityStrategy.isDisableSerialVersionUID()) {
            entityBuilder.disableSerialVersionUID();
        }

        if (entityStrategy.isEnableChainModel()) {
            entityBuilder.enableLombok();
            entityBuilder.enableChainModel();
        }

        if (entityStrategy.isEnableLombok()) {
            entityBuilder.enableLombok();
        }

        if (entityStrategy.isEnableRemoveIsPrefix()) {
            entityBuilder.enableRemoveIsPrefix();
        }

        if (entityStrategy.isEnableTableFieldAnnotation()) {
            entityBuilder.enableTableFieldAnnotation();
        }

        if (entityStrategy.isEnableActiveRecord()) {
            entityBuilder.enableActiveRecord();
        }

        if (Objects.nonNull(entityStrategy.getSuperEntityColumns())) {
            entityBuilder.addSuperEntityColumns(entityStrategy.getSuperEntityColumns());
        }

        if (Objects.nonNull(entityStrategy.getIgnoreColumns())) {
            entityBuilder.addIgnoreColumns(entityStrategy.getIgnoreColumns());
        }

        if (Objects.nonNull(entityStrategy.getTableFills())) {
            entityBuilder.addTableFills(entityStrategy.getTableFills());
        }

        entityBuilder
            .superClass(entityStrategy.getSuperClass())
            .versionColumnName(entityStrategy.getVersionColumnName())
            .versionPropertyName(entityStrategy.getVersionPropertyName())
            .logicDeleteColumnName(entityStrategy.getLogicDeleteColumnName())
            .logicDeletePropertyName(entityStrategy.getLogicDeletePropertyName())
            .naming(entityStrategy.getNaming())
            .naming(entityStrategy.getColumnNaming())
            .idType(entityStrategy.getIdType())
            .formatFileName(entityStrategy.getFileNameFormat());
    }

    static void buildServiceStrategy(StrategyConfig.Builder builder, ServiceStrategy serviceStrategy) {
        Service.Builder serviceBuilder = builder.serviceBuilder();

        if (serviceStrategy.isEnableFileOverride()) {
            serviceBuilder.enableFileOverride();
        }

        serviceBuilder
            .superServiceClass(serviceStrategy.getSuperClass())
            .superServiceImplClass(serviceStrategy.getSuperImplClass())
            .formatServiceFileName(serviceStrategy.getServiceFileNameFormat())
            .formatServiceImplFileName(serviceStrategy.getServiceImplFileNameFormat());
    }

    static void buildMapperStrategy(StrategyConfig.Builder builder, MapperStrategy mapperStrategy) {

        Mapper.Builder mapperBuilder = builder.mapperBuilder();

        if (mapperStrategy.isEnableFileOverride()) {
            mapperBuilder.enableFileOverride();
        }

        if (mapperStrategy.isEnableBaseResultMap()) {
            mapperBuilder.enableBaseResultMap();
        }

        if (mapperStrategy.isEnableBaseColumnList()) {
            mapperBuilder.enableBaseColumnList();
        }

        mapperBuilder
            .superClass(mapperStrategy.getSuperClass())
            .formatMapperFileName(mapperStrategy.getMapperFileNameFormat())
            .formatXmlFileName(mapperStrategy.getXmlFileNameFormat());
    }

    @NotNull
    private static Map<OutputFile, String> getPathInfo(PackageOption packageOption) {
        if (Objects.nonNull(packageOption.getPathInfo())) {
            return packageOption.getPathInfo();
        }

        String baseDir = getBaseDir(packageOption);
        String xmlDir = getResourcePath();

        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.xml, xmlDir);
        pathInfo.put(OutputFile.controller, baseDir + convertPackageNameToDir(packageOption.getController()));
        pathInfo.put(OutputFile.entity, baseDir + convertPackageNameToDir(packageOption.getEntity()));
        pathInfo.put(OutputFile.mapper, baseDir + convertPackageNameToDir(packageOption.getMapper()));
        pathInfo.put(OutputFile.service, baseDir + convertPackageNameToDir(packageOption.getService()));
        pathInfo.put(OutputFile.serviceImpl, baseDir + convertPackageNameToDir(packageOption.getServiceImpl()));
        return pathInfo;
    }

    private static String convertPackageNameToDir(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    @NotNull
    private static String getResourcePath() {
        return System.getProperty("user.dir") + "/src/main/resources/db/mapper/";
    }

    @NotNull
    public static String getBaseDir(PackageOption packageOption) {
        return System.getProperty("user.dir") + "/src/main/java/" + String.join("/", packageOption
            .getParent()
            .split("\\.", -1)) + "/" + packageOption.getModuleName() + "/";
    }
}

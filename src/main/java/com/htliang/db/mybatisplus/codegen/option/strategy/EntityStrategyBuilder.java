package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Entity;

import java.util.Objects;

public class EntityStrategyBuilder {
    public static void build(StrategyConfig.Builder builder, EntityStrategy entityStrategy) {
        Entity.Builder entityBuilder = builder.entityBuilder();

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

        enableFileOverride(entityStrategy, entityBuilder);
        disableSerialVersionUID(entityStrategy, entityBuilder);
        enableChainModel(entityStrategy, entityBuilder);
        enableLombok(entityStrategy, entityBuilder);
        enableRemoveIsPrefix(entityStrategy, entityBuilder);
        enableTableFieldAnnotation(entityStrategy, entityBuilder);
        enableActiveRecord(entityStrategy, entityBuilder);
        addSuperEntityColumns(entityStrategy, entityBuilder);
        addIgnoreColumns(entityStrategy, entityBuilder);
        addTableFills(entityStrategy, entityBuilder);
        enableColumnConstant(entityStrategy, entityBuilder);
    }

    private static void enableColumnConstant(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableColumnConstant()) {
            entityBuilder.enableColumnConstant();
        }
    }

    private static void addTableFills(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (Objects.nonNull(entityStrategy.getTableFills())) {
            entityBuilder.addTableFills(entityStrategy.getTableFills());
        }
    }

    private static void addIgnoreColumns(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (Objects.nonNull(entityStrategy.getIgnoreColumns())) {
            entityBuilder.addIgnoreColumns(entityStrategy.getIgnoreColumns());
        }
    }

    private static void addSuperEntityColumns(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (Objects.nonNull(entityStrategy.getSuperEntityColumns())) {
            entityBuilder.addSuperEntityColumns(entityStrategy.getSuperEntityColumns());
        }
    }

    private static void enableActiveRecord(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableActiveRecord()) {
            entityBuilder.enableActiveRecord();
        }
    }

    private static void enableTableFieldAnnotation(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableTableFieldAnnotation()) {
            entityBuilder.enableTableFieldAnnotation();
        }
    }

    private static void enableRemoveIsPrefix(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableRemoveIsPrefix()) {
            entityBuilder.enableRemoveIsPrefix();
        }
    }

    private static void enableLombok(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableLombok()) {
            entityBuilder.enableLombok();
        }
    }

    private static void enableChainModel(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableChainModel()) {
            entityBuilder.enableLombok();
            entityBuilder.enableChainModel();
        }
    }

    private static void disableSerialVersionUID(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isDisableSerialVersionUID()) {
            entityBuilder.disableSerialVersionUID();
        }
    }

    private static void enableFileOverride(EntityStrategy entityStrategy, Entity.Builder entityBuilder) {
        if (entityStrategy.isEnableFileOverride()) {
            entityBuilder.enableFileOverride();
        }
    }
}

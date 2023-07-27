package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.google.common.base.Strings;

import java.util.Objects;

public class BaseStrategyBuilder {
    public static void build(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        enableCapitalMode(builder, baseStrategy);
        enableSkipView(builder, baseStrategy);
        disableSqlFilter(builder, baseStrategy);
        enableSchema(builder, baseStrategy);

        likeTable(builder, baseStrategy);
        notLikeTable(builder, baseStrategy);

        addInclude(builder, baseStrategy);
        addExclude(builder, baseStrategy);

        addTablePrefix(builder, baseStrategy);
        addTableSuffix(builder, baseStrategy);

        addFieldPrefix(builder, baseStrategy);
        addFieldSuffix(builder, baseStrategy);
    }

    private static void addFieldSuffix(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getFieldSuffixes())) {
            builder.addFieldSuffix(baseStrategy.getFieldSuffixes());
        }
    }

    private static void addFieldPrefix(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getFieldPrefixes())) {
            builder.addFieldPrefix(baseStrategy.getFieldPrefixes());
        }
    }

    private static void addTableSuffix(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getTableSuffixes())) {
            builder.addTableSuffix(baseStrategy.getTableSuffixes());
        }
    }

    private static void addTablePrefix(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getTablePrefixes())) {
            builder.addTablePrefix(baseStrategy.getTablePrefixes());
        }
    }

    private static void addExclude(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getExcludeColumns())) {
            builder.addExclude(baseStrategy.getExcludeColumns());
        }
    }

    private static void addInclude(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (Objects.nonNull(baseStrategy.getIncludeColumns())) {
            builder.addInclude(baseStrategy.getIncludeColumns());
        }
    }

    private static void notLikeTable(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (!Strings.isNullOrEmpty(baseStrategy.getNotLikeTableValue())) {
            builder.notLikeTable(new LikeTable(baseStrategy.getNotLikeTableValue(), Objects.isNull(baseStrategy.getNotLikeTableSqlLike()) ?
                SqlLike.DEFAULT :
                baseStrategy.getNotLikeTableSqlLike()));
        }
    }

    private static void likeTable(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (!Strings.isNullOrEmpty(baseStrategy.getLikeTableValue())) {
            builder.likeTable(new LikeTable(baseStrategy.getLikeTableValue(), Objects.isNull(baseStrategy.getLikeTableSqlLike()) ?
                SqlLike.DEFAULT :
                baseStrategy.getLikeTableSqlLike()));
        }
    }

    private static void enableSchema(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (baseStrategy.isEnableSchema()) {
            builder.enableSchema();
        }
    }

    private static void disableSqlFilter(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (baseStrategy.isDisableSqlFilter()) {
            builder.disableSqlFilter();
        }
    }

    private static void enableSkipView(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (baseStrategy.isEnableSkipView()) {
            builder.enableSkipView();
        }
    }

    private static void enableCapitalMode(StrategyConfig.Builder builder, BaseStrategy baseStrategy) {
        if (baseStrategy.isEnableCapitalMode()) {
            builder.enableCapitalMode();
        }
    }
}

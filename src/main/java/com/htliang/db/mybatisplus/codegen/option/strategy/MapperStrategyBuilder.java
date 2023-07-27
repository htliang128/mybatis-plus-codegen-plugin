package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;

public class MapperStrategyBuilder {
    public static void build(StrategyConfig.Builder builder, MapperStrategy mapperStrategy) {
        Mapper.Builder mapperBuilder = builder.mapperBuilder();

        mapperBuilder
            .superClass(mapperStrategy.getSuperClass())
            .formatMapperFileName(mapperStrategy.getMapperFileNameFormat())
            .formatXmlFileName(mapperStrategy.getXmlFileNameFormat());

        enableFileOverride(mapperStrategy, mapperBuilder);
        enableBaseResultMap(mapperStrategy, mapperBuilder);
        enableBaseColumnList(mapperStrategy, mapperBuilder);
    }

    private static void enableBaseColumnList(MapperStrategy mapperStrategy, Mapper.Builder mapperBuilder) {
        if (mapperStrategy.isEnableBaseColumnList()) {
            mapperBuilder.enableBaseColumnList();
        }
    }

    private static void enableBaseResultMap(MapperStrategy mapperStrategy, Mapper.Builder mapperBuilder) {
        if (mapperStrategy.isEnableBaseResultMap()) {
            mapperBuilder.enableBaseResultMap();
        }
    }

    private static void enableFileOverride(MapperStrategy mapperStrategy, Mapper.Builder mapperBuilder) {
        if (mapperStrategy.isEnableFileOverride()) {
            mapperBuilder.enableFileOverride();
        }
    }
}

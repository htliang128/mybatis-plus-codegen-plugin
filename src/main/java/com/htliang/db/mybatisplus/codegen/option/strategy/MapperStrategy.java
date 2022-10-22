package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapperStrategy {
    private boolean enableFileOverride = false;

    private String superClass = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

    private boolean enableBaseResultMap = false;

    private boolean enableBaseColumnList = false;

    private String mapperFileNameFormat = "%s" + ConstVal.MAPPER;

    private String xmlFileNameFormat = "%s" + ConstVal.XML;
}

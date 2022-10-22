package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStrategy {
    private boolean enableFileOverride = false;

    private String superClass = "com.baomidou.mybatisplus.extension.service.IService";

    private String superImplClass = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";

    private String serviceFileNameFormat = "%s" + ConstVal.SERVICE;

    private String serviceImplFileNameFormat = "%s" + ConstVal.SERVICE_IMPL;
}

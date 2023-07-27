package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Service;

public class ServiceStrategyBuilder {
    public static void build(StrategyConfig.Builder builder, ServiceStrategy serviceStrategy) {
        Service.Builder serviceBuilder = builder.serviceBuilder();

        serviceBuilder
            .superServiceClass(serviceStrategy.getSuperClass())
            .superServiceImplClass(serviceStrategy.getSuperImplClass())
            .formatServiceFileName(serviceStrategy.getServiceFileNameFormat())
            .formatServiceImplFileName(serviceStrategy.getServiceImplFileNameFormat());

        enableFileOverride(serviceStrategy, serviceBuilder);
    }

    private static void enableFileOverride(ServiceStrategy serviceStrategy, Service.Builder serviceBuilder) {
        if (serviceStrategy.isEnableFileOverride()) {
            serviceBuilder.enableFileOverride();
        }
    }
}

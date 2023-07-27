package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.Controller;

public class ControllerStrategyBuilder {

    public static void build(StrategyConfig.Builder builder, ControllerStrategy controllerStrategy) {
        Controller.Builder controllerBuilder = builder.controllerBuilder();

        controllerBuilder
            .superClass(controllerStrategy.getSuperClass())
            .formatFileName(controllerStrategy.getFileNameFormat());

        enableFileOverride(controllerStrategy, controllerBuilder);
        enableHyphenStyle(controllerStrategy, controllerBuilder);
        enableRestStyle(controllerStrategy, controllerBuilder);
    }

    private static void enableRestStyle(ControllerStrategy controllerStrategy, Controller.Builder controllerBuilder) {
        if (controllerStrategy.isEnableRestStyle()) {
            controllerBuilder.enableRestStyle();
        }
    }

    private static void enableHyphenStyle(ControllerStrategy controllerStrategy, Controller.Builder controllerBuilder) {
        if (controllerStrategy.isEnableHyphenStyle()) {
            controllerBuilder.enableHyphenStyle();
        }
    }

    private static void enableFileOverride(ControllerStrategy controllerStrategy, Controller.Builder controllerBuilder) {
        if (controllerStrategy.isEnableFileOverride()) {
            controllerBuilder.enableFileOverride();
        }
    }
}

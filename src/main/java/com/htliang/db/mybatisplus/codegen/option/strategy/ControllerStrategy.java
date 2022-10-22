package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ControllerStrategy {
    private String superClass;

    private boolean enableFileOverride = false;

    private boolean enableHyphenStyle = false;

    private boolean enableRestStyle = false;

    private String fileNameFormat = "%s" + ConstVal.CONTROLLER;
}

package com.htliang.db.mybatisplus.codegen;

import com.htliang.db.mybatisplus.codegen.option.GlobalOption;
import com.htliang.db.mybatisplus.codegen.option.PackageOption;
import com.htliang.db.mybatisplus.codegen.option.StrategyOption;
import com.htliang.db.mybatisplus.codegen.option.TemplateOption;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodegenOptions {
    private String sqlPath;

    private GlobalOption globalOption = new GlobalOption();

    private PackageOption packageOption = new PackageOption();

    private TemplateOption templateOption = new TemplateOption();

    private StrategyOption strategyOption = new StrategyOption();
}



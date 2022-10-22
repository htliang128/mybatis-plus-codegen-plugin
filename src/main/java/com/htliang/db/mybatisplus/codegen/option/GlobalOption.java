package com.htliang.db.mybatisplus.codegen.option;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalOption {
    private boolean disableOpenDir = true;

    private String outputDir;

    private String author = "Codegen plugin";

    private boolean enableKotlin = false;

    private boolean enableSwagger = false;

    private DateType dateType = DateType.TIME_PACK;

    private String commentDate = "yyyy-MM-dd";
}

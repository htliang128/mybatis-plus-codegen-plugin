package com.htliang.db.mybatisplus.codegen.option.strategy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InjectionOption {
    private String injectionBaseDir;

    private String supplementFileName;

    private String customClassParamsFileName;

    private String customClassTemplateFileName;
}

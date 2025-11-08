package com.htliang.db.mybatisplus.codegen.dto;

import lombok.Getter;

@Getter
public enum NameEnum {
    NAMES("name"),
    CONFIG("cfg");

    NameEnum(String value) {
        this.value = value;
    }

    private final String value;
}

package com.htliang.db.mybatisplus.codegen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSuper {
    @JsonProperty("name")
    private String name;

    @JsonProperty("fields")
    private String[] fields;
}

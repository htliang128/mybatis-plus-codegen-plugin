package com.htliang.db.mybatisplus.codegen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomTable {
    @JsonProperty("name")
    private String name;

    @JsonProperty("imports")
    private String imports;

    @JsonProperty("super_class")
    private String superClass;

    @JsonProperty("interfaces")
    private String[] interfaces;

    @JsonProperty("annotations")
    private String[] annotations;

    @JsonProperty("columns")
    private CustomColumn[] columns;

    @JsonProperty("other")
    private String[] other;
}

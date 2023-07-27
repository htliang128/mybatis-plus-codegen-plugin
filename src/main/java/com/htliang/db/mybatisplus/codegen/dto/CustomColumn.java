package com.htliang.db.mybatisplus.codegen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomColumn {
    @JsonProperty("name")
    private String name;

    @JsonProperty("custom_name")
    private String customName;

    @JsonProperty("custom_type")
    private String customType;

    @JsonProperty("custom_annotations")
    private String[] customAnnotations;
}

package com.htliang.db.mybatisplus.codegen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomClass {
    @JsonProperty("package")
    private String pkg;

    @JsonProperty("imports")
    private String[] imports;

    @JsonProperty("annotations")
    private String[] annotations;

    @JsonProperty("is_abstract")
    private Boolean isAbstract;

    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("super_class")
    private String superClass;

    @JsonProperty("interfaces")
    private String[] interfaces;

    @JsonProperty("members")
    private String[] members;
}

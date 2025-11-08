package com.htliang.db.mybatisplus.codegen.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Supplement {
    @JsonProperty("tables")
    private CustomTable[] tables;

    @JsonProperty("supers")
    private CustomSuper[] supers;
}

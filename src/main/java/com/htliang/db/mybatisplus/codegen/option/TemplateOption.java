package com.htliang.db.mybatisplus.codegen.option;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplateOption {
    private boolean disableAll = false;

    private boolean disableEntity = false;

    private boolean disableService = false;

    private boolean disableServiceImpl = false;

    private boolean disableMapper = false;

    private boolean disableXml = false;

    private boolean disableController = false;

    private String entity;

    private String entityKt;

    private String service;

    private String serviceImpl;

    private String mapper;

    private String mapperXml;

    private String controller;
}

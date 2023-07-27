/*
 * Copyright (c) 2022, Hongtao Liang (lhongtao.hometown@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private boolean enableCustomTemplate = false;

    private String entity;

    private String entityKt;

    private String service;

    private String serviceImpl;

    private String mapper;

    private String mapperXml;

    private String controller;
}

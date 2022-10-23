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

package com.htliang.db.mybatisplus.codegen.option.strategy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.htliang.db.mybatisplus.codegen.option.fill.CustomTableFill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityStrategy {
    private String superClass;

    private boolean enableFileOverride = false;

    private boolean disableSerialVersionUID = false;

    private boolean enableChainModel = false;

    private boolean enableLombok = false;

    private boolean enableRemoveIsPrefix = false;

    private boolean enableTableFieldAnnotation = false;

    private boolean enableActiveRecord = false;

    private String versionColumnName;

    private String versionPropertyName;

    private String logicDeleteColumnName;

    private String logicDeletePropertyName;

    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    private NamingStrategy columnNaming = NamingStrategy.underline_to_camel;

    private String[] superEntityColumns = new String[0];

    private String[] ignoreColumns = new String[0];

    private CustomTableFill[] tableFills;

    private IdType idType;

    private String fileNameFormat = "%s";
}

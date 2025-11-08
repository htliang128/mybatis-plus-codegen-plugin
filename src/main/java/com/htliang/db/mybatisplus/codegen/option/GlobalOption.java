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

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalOption {
    private boolean disableOpenDir = true;

    private String outputDir;

    private String author = "Codegen plugin";

    private boolean enableKotlin = false;

    private boolean enableSwagger = false;

    private DateType dateType = DateType.TIME_PACK;

    private String commentDate = "yyyy-MM-dd";
}

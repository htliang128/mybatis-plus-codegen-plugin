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

import com.baomidou.mybatisplus.generator.config.OutputFile;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PackageOption {
    private String parent;

    private String moduleName;

    private String entity = "entity";

    private String service = "service";

    private String serviceImpl = "service.impl";

    private String mapper = "mapper";

    private String xml = "mapper.xml";

    private String controller = "controller";

    private Map<OutputFile, String> pathInfo;
}

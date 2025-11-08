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

import com.baomidou.mybatisplus.generator.config.ConstVal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceStrategy {
    private boolean enableFileOverride = false;

    private String superClass = "com.baomidou.mybatisplus.extension.service.IService";

    private String superImplClass = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";

    private String serviceFileNameFormat = "%s" + ConstVal.SERVICE;

    private String serviceImplFileNameFormat = "%s" + ConstVal.SERVICE_IMPL;
}

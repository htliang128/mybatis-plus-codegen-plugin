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

package com.htliang.db.mybatisplus.codegen;

import com.htliang.db.mybatisplus.codegen.option.FlywayOption;
import com.htliang.db.mybatisplus.codegen.option.GlobalOption;
import com.htliang.db.mybatisplus.codegen.option.PackageOption;
import com.htliang.db.mybatisplus.codegen.option.StrategyOption;
import com.htliang.db.mybatisplus.codegen.option.TemplateOption;
import com.htliang.db.mybatisplus.codegen.option.strategy.InjectionOption;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodegenOptions {
    private String sqlPath;

    private GlobalOption globalOption = new GlobalOption();

    private PackageOption packageOption = new PackageOption();

    private InjectionOption injectionOption = new InjectionOption();

    private TemplateOption templateOption = new TemplateOption();

    private StrategyOption strategyOption = new StrategyOption();

    private FlywayOption flywayOption = new FlywayOption();
}



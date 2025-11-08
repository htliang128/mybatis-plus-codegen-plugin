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

package com.htliang.db.mybatisplus.codegen.option.fill;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.IFill;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Setter
public class CustomTableFill implements IFill {
    private String name;

    private String fieldFill;

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public @NotNull FieldFill getFieldFill() {
        return FieldFill.valueOf(fieldFill);
    }
}

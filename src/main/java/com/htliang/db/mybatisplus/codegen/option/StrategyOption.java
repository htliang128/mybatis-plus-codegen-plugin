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

import com.htliang.db.mybatisplus.codegen.option.strategy.BaseStrategy;
import com.htliang.db.mybatisplus.codegen.option.strategy.ControllerStrategy;
import com.htliang.db.mybatisplus.codegen.option.strategy.EntityStrategy;
import com.htliang.db.mybatisplus.codegen.option.strategy.MapperStrategy;
import com.htliang.db.mybatisplus.codegen.option.strategy.ServiceStrategy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StrategyOption {
    private BaseStrategy baseStrategy = new BaseStrategy();

    private ControllerStrategy controllerStrategy = new ControllerStrategy();

    private EntityStrategy entityStrategy = new EntityStrategy();

    private ServiceStrategy serviceStrategy = new ServiceStrategy();

    private MapperStrategy mapperStrategy = new MapperStrategy();
}


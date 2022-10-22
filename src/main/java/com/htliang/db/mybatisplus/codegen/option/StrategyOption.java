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


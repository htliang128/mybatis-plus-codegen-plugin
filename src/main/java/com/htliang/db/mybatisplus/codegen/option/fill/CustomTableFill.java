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

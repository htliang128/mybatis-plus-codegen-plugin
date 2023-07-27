package com.htliang.db.mybatisplus.codegen.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class BaseUtil {
    public static String convertPackageName2Dir(@NotNull String packageName) {
        return packageName.replaceAll("\\.", "/");
    }
}

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

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.base.Strings;
import com.htliang.db.mybatisplus.codegen.dto.CustomClass;
import com.htliang.db.mybatisplus.codegen.dto.Supplement;
import com.htliang.db.mybatisplus.codegen.option.GlobalOption;
import com.htliang.db.mybatisplus.codegen.option.PackageOption;
import com.htliang.db.mybatisplus.codegen.option.StrategyOption;
import com.htliang.db.mybatisplus.codegen.option.TemplateOption;
import com.htliang.db.mybatisplus.codegen.option.engine.CustomFreemarkerEngine;
import com.htliang.db.mybatisplus.codegen.option.strategy.BaseStrategyBuilder;
import com.htliang.db.mybatisplus.codegen.option.strategy.ControllerStrategyBuilder;
import com.htliang.db.mybatisplus.codegen.option.strategy.EntityStrategyBuilder;
import com.htliang.db.mybatisplus.codegen.option.strategy.InjectionOption;
import com.htliang.db.mybatisplus.codegen.option.strategy.MapperStrategyBuilder;
import com.htliang.db.mybatisplus.codegen.option.strategy.ServiceStrategyBuilder;
import com.htliang.db.mybatisplus.codegen.utils.BaseUtil;
import com.htliang.db.mybatisplus.codegen.utils.JacksonUtil;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ConfigBuilder {
    @NotNull
    static Consumer<GlobalConfig.Builder> buildGlobalConfig(GlobalOption globalOption) {
        return builder -> {
            if (globalOption.isDisableOpenDir()) {
                builder.disableOpenDir();
            }

            if (globalOption.isEnableKotlin()) {
                builder.enableKotlin();
            }

            if (globalOption.isEnableSwagger()) {
                builder.enableSwagger();
            }

            builder
                .outputDir(globalOption.getOutputDir())
                .author(globalOption.getAuthor())
                .dateType(globalOption.getDateType())
                .commentDate(globalOption.getCommentDate());
        };
    }

    @NotNull
    static Consumer<PackageConfig.Builder> buildPackageConfig(PackageOption packageOption) {
        Map<OutputFile, String> pathInfo = getPathInfo(packageOption);

        return builder -> builder
            .parent(packageOption.getParent())
            .moduleName(packageOption.getModuleName())
            .entity(packageOption.getEntity())
            .service(packageOption.getService())
            .serviceImpl(packageOption.getServiceImpl())
            .mapper(packageOption.getMapper())
            .xml(packageOption.getXml())
            .controller(packageOption.getController())
            .pathInfo(pathInfo);
    }

    @NotNull
    static Consumer<InjectionConfig.Builder> buildInjectionConfig(InjectionOption injectionOption) {
        return builder -> builder.customMap(getObjectMap(injectionOption));
    }

    @NotNull
    static Consumer<StrategyConfig.Builder> buildStrategyConfig(StrategyOption strategyOption) {
        return builder -> {
            BaseStrategyBuilder.build(builder, strategyOption.getBaseStrategy());
            ControllerStrategyBuilder.build(builder, strategyOption.getControllerStrategy());
            EntityStrategyBuilder.build(builder, strategyOption.getEntityStrategy());
            ServiceStrategyBuilder.build(builder, strategyOption.getServiceStrategy());
            MapperStrategyBuilder.build(builder, strategyOption.getMapperStrategy());
        };
    }

    @NotNull
    static Consumer<TemplateConfig.Builder> buildTemplateConfig(TemplateOption templateOption) {
        return builder -> {
            disableTemplates(templateOption, builder);
            setCustomTemplate(templateOption, builder);
        };
    }

    @NotNull
    static AbstractTemplateEngine selectTemplateEngine(TemplateOption templateOption) {
        if (templateOption.isEnableCustomTemplate()) {
            return new CustomFreemarkerEngine(ConfigBuilder.getResourcesPath());
        }
        return new FreemarkerTemplateEngine();
    }

    @NotNull
    private static Map<OutputFile, String> getPathInfo(PackageOption packageOption) {
        if (Objects.nonNull(packageOption.getPathInfo())) {
            return packageOption.getPathInfo();
        }

        String baseDir = getBaseDir(packageOption);
        String xmlDir = getXmlPath();

        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.xml, xmlDir);
        pathInfo.put(OutputFile.controller, baseDir + convertPackageNameToDir(packageOption.getController()));
        pathInfo.put(OutputFile.entity, baseDir + convertPackageNameToDir(packageOption.getEntity()));
        pathInfo.put(OutputFile.mapper, baseDir + convertPackageNameToDir(packageOption.getMapper()));
        pathInfo.put(OutputFile.service, baseDir + convertPackageNameToDir(packageOption.getService()));
        pathInfo.put(OutputFile.serviceImpl, baseDir + convertPackageNameToDir(packageOption.getServiceImpl()));
        return pathInfo;
    }

    private static String convertPackageNameToDir(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    private static String getXmlPath() {
        return getResourcesPath() + "db/mapper/";
    }

    private static String getResourcesPath() {
        return getUserDir() + "/src/main/resources/";
    }

    @NotNull
    public static String getBaseDir(PackageOption packageOption) {
        return getUserDir() + "/src/main/java/" + String.join("/", packageOption
            .getParent()
            .split("\\.", -1)) + "/" + packageOption.getModuleName() + "/";
    }

    @NotNull
    static String getFlywayConfigPath(String flywayPackageName) {
        return getUserDir() + "/src/main/java/" + String.join("/", flywayPackageName.split("\\.", -1));
    }

    private static String getUserDir() {
        return System.getProperty("user.dir");
    }

    static String getCustomClassSavePath(CustomClass customClass) {
        return getUserDir() + "/src/main/java/" + String.join("/", customClass
            .getPkg()
            .split("\\.", -1));
    }

    private static void disableTemplates(TemplateOption templateOption, TemplateConfig.Builder builder) {
        if (templateOption.isDisableAll()) {
            builder.disable();
        }

        if (templateOption.isDisableController()) {
            builder.disable(TemplateType.CONTROLLER);
        }

        if (templateOption.isDisableEntity()) {
            builder.disable(TemplateType.ENTITY);
        }

        if (templateOption.isDisableService()) {
            builder.disable(TemplateType.SERVICE);
        }

        if (templateOption.isDisableServiceImpl()) {
            builder.disable(TemplateType.SERVICE_IMPL);
        }

        if (templateOption.isDisableMapper()) {
            builder.disable(TemplateType.MAPPER);
        }

        if (templateOption.isDisableXml()) {
            builder.disable(TemplateType.XML);
        }
    }

    private static void setCustomTemplate(TemplateOption templateOption, TemplateConfig.Builder builder) {
        if (checkIfCustomTemplateEngineEnabled(templateOption)) {
            return;
        }

        if (!Strings.isNullOrEmpty(templateOption.getEntity())) {
            builder.entity(templateOption.getEntity());
        }

        if (!Strings.isNullOrEmpty(templateOption.getEntityKt())) {
            builder.entityKt(templateOption.getEntityKt());
        }

        if (!Strings.isNullOrEmpty(templateOption.getService())) {
            builder.service(templateOption.getService());
        }

        if (!Strings.isNullOrEmpty(templateOption.getServiceImpl())) {
            builder.serviceImpl(templateOption.getServiceImpl());
        }

        if (!Strings.isNullOrEmpty(templateOption.getMapper())) {
            builder.mapper(templateOption.getMapper());
        }

        if (!Strings.isNullOrEmpty(templateOption.getMapperXml())) {
            builder.xml(templateOption.getMapperXml());
        }

        if (!Strings.isNullOrEmpty(templateOption.getController())) {
            builder.controller(templateOption.getController());
        }
    }

    private static boolean checkIfCustomTemplateEngineEnabled(TemplateOption templateOption) {
        return !templateOption.isEnableCustomTemplate();
    }

    @SneakyThrows
    private static Map<String, Object> getObjectMap(InjectionOption injectionOption) {
        Map<String, Object> cfg = new HashMap<>();
        if (Strings.isNullOrEmpty(injectionOption.getSupplementFileName())) {
            cfg.put("cfg", new HashMap<>());
            return cfg;
        }

        Resource resource = new FileSystemResource(Strings.isNullOrEmpty(injectionOption.getInjectionBaseDir()) ?
            "" :
            injectionOption.getInjectionBaseDir() + injectionOption.getSupplementFileName());
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String yamlStr = bufferedReader
                .lines()
                .collect(Collectors.joining("\n"));
            Supplement supplement = JacksonUtil.yaml2Pojo(yamlStr, Supplement.class);
            cfg.put("cfg", supplement);

            return cfg;
        }
    }
}

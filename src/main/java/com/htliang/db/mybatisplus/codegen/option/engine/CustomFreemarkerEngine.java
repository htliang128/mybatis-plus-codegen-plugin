package com.htliang.db.mybatisplus.codegen.option.engine;

import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

public class CustomFreemarkerEngine extends AbstractTemplateEngine {
    private Configuration configuration;

    private final String templateBasePath;

    public CustomFreemarkerEngine(String templateBasePath) {
        this.templateBasePath = templateBasePath;
    }

    @SneakyThrows
    @Override
    public @NotNull AbstractTemplateEngine init(@NotNull ConfigBuilder configBuilder) {
        this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        this.configuration.setDefaultEncoding(ConstVal.UTF8);
        this.configuration.setDirectoryForTemplateLoading(new File(templateBasePath));

        return this;
    }

    @Override
    public @NotNull String templateFilePath(@NotNull String filePath) {
        return filePath + ".ftl";
    }

    public void writer(@NotNull Map<String, Object> objectMap, @NotNull String templatePath, @NotNull File outputFile) throws Exception {
        Template template = this.configuration.getTemplate(templatePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
            template.process(objectMap, new OutputStreamWriter(fileOutputStream, ConstVal.UTF8));
        }
    }
}

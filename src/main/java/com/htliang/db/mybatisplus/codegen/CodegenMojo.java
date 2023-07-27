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

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.google.common.base.Strings;
import com.htliang.db.mybatisplus.codegen.dto.CustomClass;
import com.htliang.db.mybatisplus.codegen.utils.AvailablePort;
import com.htliang.db.mybatisplus.codegen.utils.JacksonUtil;
import com.htliang.db.mybatisplus.codegen.utils.SqlProcessing;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.ExceptionUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CodegenMojo extends AbstractMojo {

    public static final String JDBC_URL = "jdbc:mariadb://localhost:%s/tempdb";

    private final int PORT = AvailablePort.get();

    @Parameter
    private CodegenOptions codegenOptions;

    private DB inMemoryDB;

    private HikariDataSource dataSource;

    @SneakyThrows
    public void execute() {
        initCodegenConfig();

        initMariaDb();
        initHikariDatasource();
        initDatabaseStructure();
        executeCodegen();
        generateCustomClasses();
        generateFlywayConfig();
        inMemoryDB.stop();
    }

    @SneakyThrows
    private void generateCustomClasses() {
        String injectionBasePath = codegenOptions
            .getInjectionOption()
            .getInjectionBaseDir();
        if (Strings.isNullOrEmpty(injectionBasePath)) {
            return;
        }

        String templateFilePath = codegenOptions
            .getInjectionOption()
            .getCustomClassTemplateFileName();
        String paramsFilePath = injectionBasePath + codegenOptions
            .getInjectionOption()
            .getCustomClassParamsFileName();
        if (Strings.isNullOrEmpty(templateFilePath) || Strings.isNullOrEmpty(paramsFilePath)) {
            return;
        }

        if (!new File(paramsFilePath).exists()) {
            log.warn("params file path {} is not exist, please remove it from your pom file", paramsFilePath);
            return;
        }

        List<CustomClass> customClasses = getCustomClass(paramsFilePath);
        if (CollectionUtils.isEmpty(customClasses)) {
            return;
        }

        for (CustomClass customClass : customClasses) {
            generateCustomClass(injectionBasePath, templateFilePath, customClass);
        }
    }

    private void generateCustomClass(String injectionBasePath, String templateFilePath, CustomClass customClass)
        throws IOException {
        Map<String, Object> data = JacksonUtil.json2Map(JacksonUtil.pojo2Json(customClass), String.class, Object.class);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setDirectoryForTemplateLoading(ResourceUtils.getFile(injectionBasePath));
        configuration.setDefaultEncoding(ConstVal.UTF8);
        try {
            String configPath = ConfigBuilder.getCustomClassSavePath(customClass);
            Path path = Paths.get(configPath);
            if (!path
                .toFile()
                .exists()) {
                Files.createDirectories(path);
            }

            Path targetPath = path.resolve(customClass.getName() + ".java");
            File targetFile = targetPath.toFile();
            if (!targetFile.exists()) {
                Files.createFile(targetPath);
            }

            StringWriter stringWriter = new StringWriter();
            configuration
                .getTemplate(templateFilePath)
                .process(data, stringWriter);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFile))) {
                bufferedWriter.write(stringWriter.toString());
                bufferedWriter.flush();
            }
        } catch (IOException | TemplateException exception) {
            log.error("cannot generate code from freemarker template {}", ExceptionUtils.getStackTrace(exception));
        }
    }

    private List<CustomClass> getCustomClass(String paramsFilePath) throws IOException {
        List<CustomClass> customClasses;

        try (BufferedReader bufferedReader = new BufferedReader(
            new InputStreamReader(new FileSystemResource(paramsFilePath).getInputStream()))) {
            String yamlStr = bufferedReader
                .lines()
                .collect(Collectors.joining("\n"));
            customClasses = JacksonUtil.yamlArray2PojoList(yamlStr, CustomClass.class);
        }
        return customClasses;
    }

    private void generateFlywayConfig() throws IOException {
        if (!codegenOptions
            .getFlywayOption()
            .isEnableFlyway()) {
            return;
        }
        String configPath = ConfigBuilder.getFlywayConfigPath(codegenOptions
            .getFlywayOption()
            .getFlywayPackageName());

        Path path = Paths.get(configPath);
        Path pathCreate = Files.createDirectories(path);
        Path fileCreate = Files.createFile(pathCreate.resolve("FlywayConfig.java"));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileCreate.toFile()))) {
            String content = "package " + codegenOptions
                .getFlywayOption()
                .getFlywayPackageName() + ";\n" + "\n" + codegenOptions
                .getFlywayOption()
                .getFlywayConfig();
            writer.write(content);
            writer.flush();
        }
    }

    private void initCodegenConfig() {
        if (Strings.isNullOrEmpty(codegenOptions
            .getGlobalOption()
            .getOutputDir())) {
            codegenOptions
                .getGlobalOption()
                .setOutputDir(ConfigBuilder.getBaseDir(codegenOptions.getPackageOption()));
        }
    }

    private void initMariaDb() throws ManagedProcessException {
        DBConfigurationBuilder configurationBuilder = DBConfigurationBuilder
            .newBuilder()
            .setBaseDir(SystemUtils.JAVA_IO_TMPDIR + "/xs/MariaDB4j/base")
            .setDataDir(SystemUtils.JAVA_IO_TMPDIR + "/xs/MariaDB4j/data")
            .setDeletingTemporaryBaseAndDataDirsOnShutdown(true)
            .setPort(PORT);

        inMemoryDB = DB.newEmbeddedDB(configurationBuilder.build());
        inMemoryDB.start();
        inMemoryDB.createDB("tempdb");
    }

    private void initHikariDatasource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format(JDBC_URL, PORT));
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setDriverClassName("org.mariadb.jdbc.Driver");

        dataSource = new HikariDataSource(config);
    }

    private void initDatabaseStructure() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        String createTables = SqlProcessing.getCreateTableSqls(new FileSystemResource(codegenOptions.getSqlPath()));
        databasePopulator.addScript(new ByteArrayResource(createTables.getBytes()));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(true);
        dataSourceInitializer.afterPropertiesSet();
    }

    public void executeCodegen() {
        FastAutoGenerator
            .create(new DataSourceConfig.Builder(dataSource))
            .globalConfig(ConfigBuilder.buildGlobalConfig(codegenOptions.getGlobalOption()))
            .packageConfig(ConfigBuilder.buildPackageConfig(codegenOptions.getPackageOption()))
            .injectionConfig(ConfigBuilder.buildInjectionConfig(codegenOptions.getInjectionOption()))
            .strategyConfig(ConfigBuilder.buildStrategyConfig(codegenOptions.getStrategyOption()))
            .templateConfig(ConfigBuilder.buildTemplateConfig(codegenOptions.getTemplateOption()))
            .templateEngine(ConfigBuilder.selectTemplateEngine(codegenOptions.getTemplateOption()))
            .execute();
    }
}

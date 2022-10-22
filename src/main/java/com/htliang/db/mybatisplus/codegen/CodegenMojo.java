package com.htliang.db.mybatisplus.codegen;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import ch.vorburger.mariadb4j.DBConfigurationBuilder;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.google.common.base.Strings;
import com.htliang.db.mybatisplus.codegen.utils.SqlProcessing;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class CodegenMojo extends AbstractMojo {
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
        inMemoryDB.stop();
    }

    private void initCodegenConfig() {
        if (Strings.isNullOrEmpty(codegenOptions.getGlobalOption().getOutputDir()) ){
            codegenOptions.getGlobalOption().setOutputDir(ConfigBuilder.getBaseDir(codegenOptions.getPackageOption()));
        }
    }

    private void initMariaDb() throws ManagedProcessException {
        DBConfigurationBuilder configurationBuilder = DBConfigurationBuilder
            .newBuilder()
            .setBaseDir(SystemUtils.JAVA_IO_TMPDIR + "/xs/MariaDB4j/base")
            .setDataDir(SystemUtils.JAVA_IO_TMPDIR + "/xs/MariaDB4j/data")
            .setDeletingTemporaryBaseAndDataDirsOnShutdown(true)
            .setPort(33066);

        inMemoryDB = DB.newEmbeddedDB(configurationBuilder.build());
        inMemoryDB.start();
        inMemoryDB.createDB("tempdb");
    }

    private void initHikariDatasource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:33066/tempdb");
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
            .strategyConfig(ConfigBuilder.buildStrategyConfig(codegenOptions.getStrategyOption()))
            .templateConfig(ConfigBuilder.buildTemplateConfig(codegenOptions.getTemplateOption()))
            .templateEngine(new FreemarkerTemplateEngine())
            .execute();
    }
}

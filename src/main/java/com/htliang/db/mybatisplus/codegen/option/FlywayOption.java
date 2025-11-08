package com.htliang.db.mybatisplus.codegen.option;

import lombok.Getter;

@Getter
public class FlywayOption {
    private static final String EOL = System.lineSeparator();

    private boolean enableFlyway = false;

    private String flywayPackageName = "";

    private String flywayConfig = "import org.flywaydb.core.Flyway;" + EOL
        + "import org.springframework.beans.factory.annotation.Autowired;" + EOL
        + "import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;" + EOL
        + "import org.springframework.context.annotation.Bean;" + EOL + "" + EOL
        + "import org.springframework.context.annotation.Configuration;" + EOL + "" + EOL

        + "import javax.sql.DataSource;" + EOL + "" + EOL

        + "@Configuration" + EOL
        + "public class FlywayConfig {" + EOL
        + "    @Bean(name = \"dbFlywayMigration\", initMethod = \"migrate\")" + EOL
        + "    @ConditionalOnProperty(name = \"test.mode\", havingValue = \"false\", matchIfMissing = true)" + EOL
        + "    public Flyway createFlyway(@Autowired DataSource dataSource) {" + EOL
        + "        return Flyway" + EOL
        + "            .configure()" + EOL
        + "            .dataSource(dataSource)" + EOL
        + "            .locations(\"classpath:db/ddl/update\")" + EOL
        + "            .baselineOnMigrate(true)" + EOL
        + "            .cleanDisabled(true)" + EOL
        + "            .load();" + EOL
        + "    }" + EOL
        + "}";
}

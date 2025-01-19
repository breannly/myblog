package com.myblog.config.db;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Value("${flyway.locations}")
    private String locations;

    @Value("${flyway.baselineOnMigrate}")
    private boolean baselineOnMigrate;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
            .dataSource(dataSource)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate)
            .load();

        flyway.migrate();
        return flyway;
    }
}

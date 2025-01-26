package com.myblog.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(value = {WebConfig.class, JdbcConfig.class, FlywayConfig.class})
@PropertySource(value = "classpath:application.properties")
public class ApplicationConfig {
}

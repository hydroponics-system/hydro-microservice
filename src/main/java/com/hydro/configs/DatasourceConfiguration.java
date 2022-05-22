package com.hydro.configs;

import javax.sql.DataSource;

import com.hydro.common.sql.DataSourceDbBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Application Configs for running the application.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@Configuration
public class DatasourceConfiguration {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * Datasource configuration. This will get called anywhere a {@link DataSource}
     * is autowired into the class.
     * 
     * @return {@link DataSource} object.
     */
    @Bean
    @Profile(value = { "local", "development", "production" })
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        return DataSourceDbBuilder.create().useDefaultProperties().url(dbUrl).username(dbUsername).password(dbPassword)
                .build();
    }
}
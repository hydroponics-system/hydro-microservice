package com.hydro.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
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

    private final String DB_URL_PROPERTIES = "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

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
        return DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver").url(dbUrl + DB_URL_PROPERTIES)
                .username(dbUsername).password(dbPassword).build();
    }
}
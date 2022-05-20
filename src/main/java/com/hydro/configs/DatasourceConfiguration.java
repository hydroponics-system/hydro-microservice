package com.hydro.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(dbUrl + DB_URL_PROPERTIES);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }
}
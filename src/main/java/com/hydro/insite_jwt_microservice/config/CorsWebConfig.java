package com.hydro.insite_jwt_microservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Configuration to setting allowed origins and methods on the response
 * body.
 * 
 * @author Sam Butler
 * @since July 29, 2022
 */
@Configuration
public class CorsWebConfig {

    @Value("${security.allowed.domains}")
    private String allowedDomains;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedHeaders("Authorization", "Cache-Control", "Content-Type")
                        .allowedOrigins(allowedDomains).allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}

package com.hydro.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Swagger config for showing API's
 * 
 * @author Sam Butler
 * @since April 27, 2022
 */
@Configuration
public class SwaggerConfiguration {

    @Value("${info.name}")
    private String name;

    @Value("${info.version}")
    private String version;

    @Value("${info.description}")
    private String description;

    @Value("${info.contact.name}")
    private String contactName;

    @Value("${info.contact.email}")
    private String contactEmail;

    @Value("${info.contact.url}")
    private String contactUrl;

    @Value("${info.license.type}")
    private String licenseType;

    @Value("${info.license.url}")
    private String licenseUrl;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title(name).version(version).description(description)
                .contact(new Contact().name(contactName).url(contactUrl).email(contactEmail))
                .license(new License().name(licenseType).url(licenseUrl)));
    }
}

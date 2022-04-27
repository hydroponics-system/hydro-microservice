package com.hydro.swagger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.classmate.TypeResolver;
import com.hydro.app.user.client.domain.request.UserGetRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger config for showing API's
 * 
 * @author Sam Butler
 * @since April 27, 2022
 */
@Configuration
public class SpringSwaggerConfig {

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

    /**
     * API bean that will init the swagger documentation to be served.
     * 
     * @param typeResolver The type resolver to add any other model classes.
     * @return {@link Docket} of the swagger configs.
     */
    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select()
                .apis(RequestHandlerSelectors.basePackage("com.hydro")).paths(PathSelectors.any()).build()
                .additionalModels(typeResolver.resolve(UserGetRequest.class));
    }

    /**
     * Method for setting common API info for the swagger documentation to show on
     * the UI.
     * 
     * @return The {@link ApiInfo} object with the data.
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(name, description, version, "", contactInfo(), licenseType, licenseUrl,
                Collections.emptyList());
    }

    /**
     * Get swagger default contact information for the ui.
     * 
     * @return {@link Contact} object containing information.
     */
    private Contact contactInfo() {
        return new Contact(contactName, contactUrl, contactEmail);
    }

    /**
     * Creates the API key for a user to send authenticated requests from the
     * swagger ui.
     * 
     * @return {@link ApiKey} with the authorization needed.
     */
    private ApiKey apiKey() {
        return new ApiKey("Authorization Token", "Authorization", "header");
    }

    /**
     * Gets the security context for the default auth to be used when sending
     * request from the UI.
     * 
     * @return {@link SecurityContext} with the default auth.
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    /**
     * Gets the default auth for the swagger application.
     * 
     * @return List of {@link SecurityReference} that adds the auth to the request.
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "Token to make API calls");
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}

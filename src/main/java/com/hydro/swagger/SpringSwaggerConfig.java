package com.hydro.swagger;

import java.util.Collections;

import com.fasterxml.classmate.TypeResolver;
import com.hydro.app.user.client.domain.request.UserGetRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringSwaggerConfig {

    @Bean
    public Docket api(TypeResolver typeResolver) {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.hydro")).paths(PathSelectors.any()).build()
                .additionalModels(typeResolver.resolve(UserGetRequest.class));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Hydroponics REST Api", "Restful API's for managing a hydroponic system.", "Snapshot", "",
                new Contact("Sam Butler", "https://github.com/sambutler1017/hydro-microservice",
                        "sambutler1017@icloud.com"),
                "MIT License", "https://github.com/sambutler1017/hydro-microservice/blob/master/LICENSE.md",
                Collections.emptyList());
    }
}

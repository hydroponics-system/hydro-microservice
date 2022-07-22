package com.hydro.insite_common_microservice.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application Configs for logger data.
 * 
 * @author Sam Butler
 * @since July 19, 2022
 */
@Configuration
public class LoggerConfiguration {

    @Bean
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getDeclaredType());
    }
}

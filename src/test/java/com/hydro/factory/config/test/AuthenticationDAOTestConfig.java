package com.hydro.factory.config.test;

import org.springframework.context.annotation.ComponentScan;

/**
 * Defines the Authentication respository path.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@ComponentScan(basePackages = { "com.hydro.app.auth.dao" })
public class AuthenticationDAOTestConfig {

}

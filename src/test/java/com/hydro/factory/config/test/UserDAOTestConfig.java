package com.hydro.factory.config.test;

import org.springframework.context.annotation.ComponentScan;

/**
 * Defines the User respository path.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
@ComponentScan(basePackages = { "com.hydro.insite_user_microservice.dao" })
public class UserDAOTestConfig {}

package com.hydro.insite_jwt_microservice.utility;

import org.springframework.stereotype.Service;

import com.hydro.insite_common_microservice.enums.Environment;

/**
 * Used to set and get the property files and active profile for the application
 * based on the current environment the application is running in.
 *
 * @author Sam Butler
 * @since April 30, 2022
 */
@Service
public class ActiveProfile {
    private static final String ACTIVE_PROFILE = "spring.profiles.active";
    private static final String APP_ENV = "APP_ENVIRONMENT";
    private static final String SIGNING_KEY = "JWT_SIGNING_KEY";
    private static final String DEFAULT_KEY = "local-key";

    /**
     * Method to set the current active profile the application is running in
     */
    public void setEnvironmentProperties() {
        System.setProperty(ACTIVE_PROFILE, Environment.get(System.getenv(APP_ENV)).toString().toLowerCase());
    }

    /**
     * This method gets the current environment
     *
     * @return string of the environment currently running
     */
    public Environment getEnvironment() {
        return Environment.get(System.getProperty(ACTIVE_PROFILE));
    }

    /**
     * Gets the signing key for jwt tokens
     * 
     * @return String of the signing key to use.
     */
    public String getSigningKey() {
        return isLocalEnvironment() ? DEFAULT_KEY : System.getenv(SIGNING_KEY);
    }

    /**
     * Determines if a local environment instance is being run.
     * 
     * @return boolean if the local instance is being run.
     */
    public boolean isLocalEnvironment() {
        return getEnvironment().equals(Environment.LOCAL);
    }
}

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
    private static final String HEROKU_ENV_PATH = "/app/src/main";
    private static final String LOCAL_ENV_PATH = "src/main";
    private static final String ACTIVE_PROFILE_TAG = "spring.profiles.active";
    private static final String APP_ENV = "APP_ENVIRONMENT";

    /**
     * Method to set the current active profile the application is running in
     */
    public void setPropertyFile() {
        System.setProperty(ACTIVE_PROFILE_TAG, getEnvironment().toString().toLowerCase());
    }

    /**
     * This method gets the path to the property file based on the environment
     *
     * @return string of the path to the set property file
     */
    public String getPropertyFilePath() {
        return String.format("%s/resources/%s", getEnvironmentUrl(), getAppPropertiesName());
    }

    /**
     * This method gets the current environment
     *
     * @return string of the environment currently running
     */
    public Environment getEnvironment() {
        if (System.getenv(APP_ENV) != null) {
            return Environment.getEnvrionment(System.getenv(APP_ENV));
        } else {
            return Environment.LOCAL;
        }
    }

    /**
     * Gets the application propteries file name.
     * 
     * @return String of the application file name
     */
    public String getAppPropertiesName() {
        return String.format("application-%s.properties", getEnvironment().toString().toLowerCase());
    }

    /**
     * Gets the environment url
     *
     * @return string of the environment url
     */
    public String getEnvironmentUrl() {
        if (getEnvironment().equals(Environment.LOCAL)) {
            return LOCAL_ENV_PATH;
        } else {
            return HEROKU_ENV_PATH;
        }
    }

    /**
     * Gets the signing key for jwt tokens
     * 
     * @return String of the signing key to use.
     */
    public String getSigningKey() {
        if (isLocalEnvironment()) {
            return "local-key";
        } else {
            return System.getenv("JWT_SIGNING_KEY");
        }
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

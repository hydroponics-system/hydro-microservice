package com.hydro.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import com.hydro.common.enums.Environment;

import org.springframework.stereotype.Service;

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
    private static final String SEND_GRID_KEY = "SENDGRID_API_KEY";

    /**
     * Method to set the current active profile the application is running in
     */
    public void setPropertyFile() {
        System.setProperty("spring.profiles.active", getEnvironment().toString().toLowerCase());
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
        if (System.getenv("APP_ENVIRONMENT") != null) {
            return Environment.getEnvrionment(System.getenv("APP_ENVIRONMENT"));
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
        if (isLocalEnvironment()) {
            return "application-local.properties";
        } else {
            return String.format("application-%s.properties", getEnvironment().toString().toLowerCase());
        }
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

    /**
     * Gets the signing key property for sending emails
     * 
     * @return {@link String} of the key
     * @throws IOException
     */
    public String getSendGridSigningKey() throws IOException {
        if (isLocalEnvironment()) {
            String sendGridField = String.format("%s=", SEND_GRID_KEY);
            BufferedReader br = new BufferedReader(new FileReader(getPropertyFilePath()));
            String gridSigning = br.lines().collect(Collectors.joining("\n"));
            br.close();

            gridSigning = gridSigning.substring(gridSigning.indexOf(sendGridField) + sendGridField.length());
            int endIndex = gridSigning.indexOf("\n") == -1 ? gridSigning.length() : gridSigning.indexOf("\n");
            return endIndex > -1 ? gridSigning.substring(0, endIndex) : gridSigning;
        } else {
            return System.getenv(SEND_GRID_KEY);
        }
    }
}

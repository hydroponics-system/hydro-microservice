package com.hydro.insite_jwt_microservice.utility;

import org.springframework.stereotype.Service;

import com.hydro.insite_common_microservice.enums.Environment;
import com.hydro.insite_common_microservice.util.HydroLogger;

/**
 * Information about the sessions environment information from the jwt instance.
 * 
 * @author Sam Butler
 * @since July 25, 2022
 */
@Service
public class JwtEnvironment {

    private static final String ACTIVE_PROFILE = "APP_ENVIRONMENT";
    private static final String SIGNING_KEY = "JWT_SIGNING_KEY";
    private static final String DEFAULT_KEY = "local-key";

    private static HydroLogger LOGGER = new HydroLogger(JwtEnvironment.class);

    /**
     * Gets the current active profile environment.
     *
     * @return string of the environment currently running
     */
    public static Environment getEnvironment() {
        LOGGER.info("PRofile: " + Environment.get(System.getProperty(ACTIVE_PROFILE)));
        return Environment.get(System.getProperty(ACTIVE_PROFILE));
    }

    /**
     * Gets the signing key for jwt tokens
     * 
     * @return String of the signing key to use.
     */
    public static String getSigningKey() {
        return isLocalEnvironment() ? DEFAULT_KEY : System.getenv(SIGNING_KEY);
    }

    /**
     * Determines if a local environment instance is being run.
     * 
     * @return boolean if the local instance is being run.
     */
    public static boolean isLocalEnvironment() {
        return getEnvironment().equals(Environment.LOCAL);
    }
}

package com.hydro.insite_common_microservice.enums;

/**
 * Enum to map environments to objects.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public enum Environment {
    PRODUCTION, DEVELOPMENT, LOCAL, TEST;

    public static Environment getEnvrionment(String text) {
        for (Environment w : Environment.values())
            if (w.toString().equals(text.toUpperCase()))
                return w;
        return LOCAL;
    }
}

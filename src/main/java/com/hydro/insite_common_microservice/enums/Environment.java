package com.hydro.insite_common_microservice.enums;

/**
 * Enum to map environments to objects.
 * 
 * @author Sam Butler
 * @since July 31, 2021
 */
public enum Environment {
    PRODUCTION,
    DEVELOPMENT,
    LOCAL;

    /**
     * Will get the environment object enum from the passed in text value. If the
     * enum is invalid it will return the {@link Environment#LOCAL} environment by
     * default.
     * 
     * @param text The text to process.
     * @return {@link Environment} Object
     */
    public static Environment get(String text) {
        if(text == null) {
            return LOCAL;
        }

        for(Environment w : Environment.values())
            if(w.toString().equals(text.toUpperCase())) return w;
        return LOCAL;
    }
}

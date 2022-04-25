package com.hydro.factory.resolver;

import java.util.Map;

import org.springframework.test.context.ActiveProfilesResolver;

/**
 * Resolver method that decides what property file to use when running test.
 * 
 * @author Sam Butler
 * @since April 25, 2022
 */
public class ActiveProfileTestResolver implements ActiveProfilesResolver {

    @Override
    public String[] resolve(Class<?> testClass) {
        Map<String, String> env = System.getenv();
        String profile;
        if (env.containsKey("TEST_ENV")) {
            profile = "test";
        } else {
            profile = "test-local";
        }
        return new String[] { profile };
    }

}

package com.hydro.factory.resolver;

import java.util.Map;

import com.hydro.factory.globals.GlobalsTest;

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
        return new String[] { env.containsKey("TEST_ENV") ? GlobalsTest.PRODUCTION_TEST : GlobalsTest.LOCAL_TEST };
    }
}

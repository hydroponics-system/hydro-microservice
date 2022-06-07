package com.hydro.jwt.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

/**
 * JWT global variables class
 * 
 * @author Sam butler
 * @since Dec 5, 2020
 */
public class JwtGlobals {

    /**
     * Void endpoints that do not need to be validated with a jwt token.
     */
    public static final Map<String, List<HttpMethod>> VOID_ENDPOINTS = new HashMap<String, List<HttpMethod>>() {

        private static final long serialVersionUID = 1L;
        {
            // ==================
            // Authentication App
            // ===================

            // POST -> User Authentication Endpoint
            put("/authenticate", List.of(HttpMethod.POST));

            // ===================
            // User App
            // ===================

            // GET -> Get Email Exist status Endpoint
            put("/api/user-app/user-profile/check-email", List.of(HttpMethod.GET));

            // POST -> Create User Endpoint
            put("/api/user-app/user-profile", List.of(HttpMethod.POST));

            // ===================
            // Swagger
            // ===================
            put("/swagger-ui", List.of(HttpMethod.GET));
            put("/swagger-resources", List.of(HttpMethod.GET));
            put("/v3/api-docs", List.of(HttpMethod.GET));
        }
    };
}

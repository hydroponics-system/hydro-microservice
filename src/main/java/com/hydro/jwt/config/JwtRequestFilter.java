package com.hydro.jwt.config;

import static com.hydro.jwt.config.JwtGlobals.VOID_ENDPOINTS;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * All Request get filtered here to confirm that it has a valid jwt token before
 * accessing data. If the request should not be filtered then the
 * {@link #shouldNotFilter(HttpServletRequest)} will catch it and continue to
 * the endpoint call.
 * 
 * @author Sam butler
 * @since Aug 6, 2021
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenValidator JWTValidator;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        if (JWTValidator.validateRequest(req)) {
            chain.doFilter(req, res);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        List<HttpMethod> voidList = getVoidEnpoint(request.getRequestURI());
        HttpMethod requestMethodType = HttpMethod.valueOf(request.getMethod());

        return voidList.size() > 0 && voidList.contains(requestMethodType);
    }

    /**
     * Helper method for getting the void endpoint for the given uri. If the URI
     * does not match any of the void endpoints then it will return an empty list
     * and the request will be run through the request filter.
     * 
     * @param uri The uri to validate as a void endpoint.
     * @return List {@link HttpMethod} of the methods that are allowed through.
     */
    private List<HttpMethod> getVoidEnpoint(String uri) {
        Stream<List<HttpMethod>> opList = VOID_ENDPOINTS.entrySet().stream().filter(res -> uri.contains(res.getKey()))
                .map(res -> res.getValue());
        return opList.findFirst().orElse(Collections.emptyList());
    }

}
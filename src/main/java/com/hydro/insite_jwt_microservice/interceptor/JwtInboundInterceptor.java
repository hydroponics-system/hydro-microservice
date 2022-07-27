package com.hydro.insite_jwt_microservice.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
public class JwtInboundInterceptor extends OncePerRequestFilter {

    @Autowired
    private JwtTokenValidator JWTValidator;

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        if(JWTValidator.validateRequest(req)) {
            chain.doFilter(req, res);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return excludedMatchers().stream().anyMatch(matcher -> matcher.matches(request));
    }

    /**
     * Defined filtered matchers that do not need authentication. This will be
     * filtered out from the {@link JWTValidator}.
     * 
     * @return List of {@link AntPathRequestMatcher} matchers.
     */
    private List<AntPathRequestMatcher> excludedMatchers() {
        List<AntPathRequestMatcher> matchers = new ArrayList<>();
        matchers.add(new AntPathRequestMatcher("/authenticate", "POST"));
        matchers.add(new AntPathRequestMatcher("/api/user-app/profile/check-email", "GET"));
        matchers.add(new AntPathRequestMatcher("/api/user-app/profile", "POST"));
        matchers.add(new AntPathRequestMatcher("/v3/api-docs/**"));
        matchers.add(new AntPathRequestMatcher("/swagger-ui/**"));
        return matchers;
    }
}
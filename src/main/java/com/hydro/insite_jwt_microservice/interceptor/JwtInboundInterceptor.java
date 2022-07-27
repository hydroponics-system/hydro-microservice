package com.hydro.insite_jwt_microservice.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class JwtInboundInterceptor implements Filter {

    @Autowired
    private JwtTokenValidator JWTValidator;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if(JWTValidator.validateRequest((HttpServletRequest) req)) {
            chain.doFilter(req, res);
        }
    }
}
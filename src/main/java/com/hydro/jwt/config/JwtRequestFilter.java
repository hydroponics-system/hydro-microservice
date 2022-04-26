package com.hydro.jwt.config;

import static com.hydro.jwt.config.JwtGlobals.VOID_ENDPOINTS;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        List<List<HttpMethod>> voidList = VOID_ENDPOINTS.entrySet().stream()
                .filter(res -> request.getRequestURI().contains(res.getKey())).map(res -> res.getValue())
                .collect(Collectors.toList());
        HttpMethod requestMethodType = HttpMethod.valueOf(request.getMethod());

        return voidList.size() > 0 && (voidList.size() > 1 || voidList.get(0).contains(requestMethodType));
    }

}
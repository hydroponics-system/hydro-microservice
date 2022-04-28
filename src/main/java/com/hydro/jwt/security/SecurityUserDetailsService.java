package com.hydro.jwt.security;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Helper service method to load empty {@link UserDetails} enity if the user is
 * not authenticated.
 * 
 * @author Sam Butler
 * @since March 4, 2022
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) {
        return new User("", "", new ArrayList<>());
    }
}

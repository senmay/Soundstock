package com.soundstock.services.helpers;

import com.soundstock.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getName().equals("prometheus") && authentication.getCredentials().equals("prometheus")) {//todo secure values somehow. @Value
            log.info("Authenticating prometheus user: {}", authentication.getName());
            return new UsernamePasswordAuthenticationToken(
                    authentication.getName(), authentication.getCredentials().toString(), Collections.singleton(new SimpleGrantedAuthority("ENDPOINT_ADMIN")));
        }
        log.info("Authenticating user with name: {}", authentication.getName());
        UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
        if (userDetails != null && passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            log.info("Authentication successful for user: {}", authentication.getName());
            return new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString(), userDetails.getAuthorities());
        } else {
            log.warn("Bad credentials for user: {}", authentication.getName());
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
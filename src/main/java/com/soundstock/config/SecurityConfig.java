package com.soundstock.config;

import com.soundstock.services.CustomAuthenticationProvider;
import com.soundstock.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final String[] whiteListedEndpoints = {"/user/v1/register", "/user/v1/confirm", "/user/v1/login", "/song/v1/getAll"};
    private final String[] endpointsWithOnlyAdminPrivileges = {"/user/v1/userlist"};
    private final String[] endpointsWithOnlyUserPrivileges = {"/user/v1/jwt"};
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> {
                    request
                            .requestMatchers(whiteListedEndpoints).permitAll();
                    request
                            .requestMatchers(endpointsWithOnlyUserPrivileges).hasAnyAuthority(USER,ADMIN);
                    request
                            .requestMatchers(endpointsWithOnlyAdminPrivileges).hasAnyAuthority(ADMIN);
                }).authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(8);
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new CustomAuthenticationProvider(userService, passwordEncoder());
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


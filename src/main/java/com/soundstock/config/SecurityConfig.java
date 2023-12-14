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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //TODO create more String[] with different type of requests
    private final String[] whiteListedEndpoints = {
            "/user/v1/register", "/user/v1/confirm", "/user/v1/login", "/user/v1/refresh", "/user/v1/userlist",
            "/song/v1/getSong", "/stock/v1/id", "/song/v1/getAll", "/artist/v1/getAll", "/artist/v1/id", "/artist/v1/name","/album/v1/getAll",
            "/album/v1/id", "/order/v1/all"};
    private final String[] endpointsWithOnlyAdminPrivileges = {
            "/song/v1/add",
            "/stock/v1/add", "/stock/v1/delete", "/stock/v1/all",
            "/artist/v1/add", "/artist/v1/id",
            "/album/v1/add"};
    private final String[] endpointsWithOnlyUserPrivileges = {"/user/v1/jwt", "/order/v1/add",
            "/order/v1/orderid", "/order/v1/my-orders", "/order/v1/test", "/stock/v1/stocklist"};
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
                            .requestMatchers(endpointsWithOnlyUserPrivileges).hasAnyAuthority(USER, ADMIN);

                    request
                            .requestMatchers(endpointsWithOnlyAdminPrivileges).hasAnyAuthority(ADMIN);
                }).authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(userService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Bearer", "Refresh_token"));
        configuration.addExposedHeader("Access_token");
        configuration.addExposedHeader("Refresh_token");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}


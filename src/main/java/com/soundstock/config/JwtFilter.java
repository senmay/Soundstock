package com.soundstock.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Usuwanie "Bearer " z tokena
        }

        if (token == null || !verifyToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServletException("Wrong or empty header");
        }
        filterChain.doFilter(request, response);
    }

    private boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secretpassword");
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
        } catch (JWTVerificationException exception){
            return false;
        }
        return true;
    }
}

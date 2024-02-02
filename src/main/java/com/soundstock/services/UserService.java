package com.soundstock.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.soundstock.enums.TokenType;
import com.soundstock.enums.UserRole;
import com.soundstock.exceptions.ExpiredDate;
import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.UserMapper;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.TokenEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.TokenRepository;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.soundstock.exceptions.ErrorMessages.USERNAME_OR_EMAIL_EXISTS;
import static com.soundstock.exceptions.ErrorMessages.USER_NOT_FOUND;

@Slf4j
@Service

public class UserService implements UserDetailsService {
    @Value("$jwt.secret")
    private String secretKey;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(TokenRepository tokenRepository, UserMapper userMapper, UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager) {
        this.tokenRepository = tokenRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void registerUser(UserDTO userDTO, HttpServletResponse response) {

        if (userRepository.existsByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())) {
            throw new EntityExistsException(USERNAME_OR_EMAIL_EXISTS);
        }
        UserEntity userEntity = userMapper.mapToUserEntity(userDTO);
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
        response.addHeader("token", createAndStoreRegistrationToken(userDTO.getEmail()).getValue());
    }

    @Transactional
    public void loginWithJWT(UserDTO userDTO, HttpServletResponse response) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserEntity authenticatedUser = userRepository.findByUsername(userDTO.getUsername()).get();
        String accessToken = generateAccessToken(authenticatedUser);
        String refreshToken = generateRefreshToken(authenticatedUser);

        // Deactivate all previous JWT tokens for this user
        deactivateAllTokenForUser(authenticatedUser.getEmail());

        // Save new JWT token in the database
        TokenEntity jwtAccessToken = new TokenEntity(accessToken, TokenType.ACCESS, authenticatedUser.getEmail());
        TokenEntity jwtRefreshToken = new TokenEntity(refreshToken, TokenType.REFRESH, authenticatedUser.getEmail());
        tokenRepository.save(jwtAccessToken);
        tokenRepository.save(jwtRefreshToken);

        response.addHeader("Access_token", accessToken);
        response.addHeader("Refresh_token", refreshToken);
        log.info("Role for user " + userDTO.getUsername() + ": " + authenticatedUser.getRole());
    }


    public void confirmUser(String tokenValue) {
        TokenEntity tokenEntity = tokenRepository.findByValue(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));

        if (tokenEntity.getUsed()) {
            throw new IllegalStateException("Token has already been used");
        }
        if (tokenEntity.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredDate("Token is expired");
        }

        UserEntity user = userRepository.findByEmail(tokenEntity.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        user.setEnabled(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);

        tokenEntity.setUsed(true);
        tokenRepository.save(tokenEntity);
    }

    private TokenEntity createAndStoreRegistrationToken(String email) {
        TokenEntity tokenEntity = new TokenEntity(UUID.randomUUID().toString(), TokenType.REGISTRATION, email);
        tokenRepository.save(tokenEntity);
        return tokenEntity;
    }

    private String generateAccessToken(UserEntity user) {
        long currentTimeMillis = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role", String.valueOf(user.getRole()))
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + 30000))
                .sign(algorithm);
    }

    private String generateRefreshToken(UserEntity user) {
        long currentTimeMillis = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role", String.valueOf(user.getRole()))
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + 604800000))
                .sign(algorithm);
    }

    @Transactional
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = request.getHeader("Refresh_token");
            if (!isRefreshTokenValid(refreshToken)) {
                throw new IllegalArgumentException("Invalid or expired refresh token");
            }

            String userEmail = getUserEmailFromRefreshToken(refreshToken);
            UserEntity user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + userEmail));

            String newAccessToken = generateAccessToken(user);
            TokenEntity jwtAccessToken = new TokenEntity(newAccessToken, TokenType.ACCESS, user.getEmail());
            tokenRepository.save(jwtAccessToken);

            response.addHeader("Access_token", newAccessToken);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refreshing token: " + e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", username);
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if (byUsername.isEmpty()) {
            log.warn("User not found for username: {}", username);
            throw new ObjectNotFound(USER_NOT_FOUND);
        }
        log.info("User found: {}", byUsername.get());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(byUsername.get().getRole().toString()));
        return new org.springframework.security.core.userdetails.User(username, byUsername.get().getPassword(), authorities);
    }

    public List<UserDTO> getAllUsers() {
        return userMapper.mapToUserList(userRepository.findAll());
    }

    private void deactivateAllTokenForUser(String email) {
        List<TokenEntity> tokens = tokenRepository.findByUserEmailAndTypeAndUsed(email,TokenType.BEARER, false);
        for(TokenEntity token: tokens){
            token.setUsed(true);
        }
        tokenRepository.saveAll(tokens);
    }
    public boolean isRefreshTokenValid(String refreshToken) {
        return tokenRepository.findByValue(refreshToken)
                .map(tokenEntity ->
                        !tokenEntity.getUsed() &&
                                tokenEntity.getExpirationDate().isAfter(LocalDateTime.now()) &&
                                userRepository.existsByEmail(tokenEntity.getUserEmail()))
                .orElse(false);
    }
    public String getUserEmailFromRefreshToken(String refreshToken) {
        return tokenRepository.findByValue(refreshToken)
                .map(TokenEntity::getUserEmail)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
    }
}

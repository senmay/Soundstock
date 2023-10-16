package com.soundstock.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.soundstock.enums.TokenType;
import com.soundstock.enums.UserRole;
import com.soundstock.exceptions.ExpiredDate;
import com.soundstock.mapper.UserMapper;
import com.soundstock.model.User;
import com.soundstock.model.entity.TokenEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.TokenRepository;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
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

import static com.soundstock.exceptions.ErrorMessages.ENTITY_EXISTS;
import static com.soundstock.exceptions.ErrorMessages.USER_NOT_FOUND;

@Slf4j
@Service

public class UserService implements UserDetailsService {
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(TokenRepository tokenRepository, UserMapper userMapper, UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager) {
        this.tokenRepository = tokenRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    private final AuthenticationManager authenticationManager;

    @Transactional
    public String registerUser(User user) {

        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new EntityExistsException(ENTITY_EXISTS);
        }
        UserEntity userEntity = userMapper.mapToUserEntity(user);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);

        return createAndStoreRegistrationToken(user.getEmail()).getValue();
    }

    @Transactional
    public String loginWithJWT(User user, HttpServletResponse response){
//        Optional<UserEntity> existingUser = userRepository.findByUsername(user.getUsername());
//        if (existingUser.isEmpty()){
//            throw new RuntimeException(USER_NOT_FOUND);
//        }
//        if (!existingUser.get().getPassword().equals(user.getPassword())) {
//            throw new RuntimeException("Incorrect password");
//        }
//        return generateToken(user);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = generateToken(user);
        response.addHeader("JWT_token", token);
        return "Logged in";

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

    private String generateToken(User user){
        long currentTimeMillis = System.currentTimeMillis();
        Algorithm algorithm = Algorithm.HMAC256("secretpassword");
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("role", String.valueOf(user.getRole()))
                .withIssuedAt(new Date(currentTimeMillis))
                .withExpiresAt(new Date(currentTimeMillis + 20000))
                .sign(algorithm);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> byUsername = userRepository.findByUsername(username);
        if (byUsername.isEmpty()){
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(byUsername.get().getRole().toString()));
        return new org.springframework.security.core.userdetails.User(username,byUsername.get().getPassword(), authorities);
    }
}

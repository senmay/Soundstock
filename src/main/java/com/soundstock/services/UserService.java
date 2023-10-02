package com.soundstock.services;

import com.soundstock.enums.TokenType;
import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapper;
import com.soundstock.model.User;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.TokenEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.TokenRepository;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.soundstock.exceptions.ErrorMessages.ENTITY_EXISTS;
import static com.soundstock.exceptions.ErrorMessages.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void registerUser(User user) {

        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new EntityExistsException(ENTITY_EXISTS);
        }
        userRepository.save(userMapper.mapToUserEntity(user));

        createAndStoreRegistrationToken(user.getEmail());
    }

    public void confirmUser(String tokenValue) {
        TokenEntity tokenEntity = tokenRepository.findByValue(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));

        if (tokenEntity.getUsed()) {
            throw new IllegalStateException("Token has already been used");
        }
        UserEntity user = userRepository.findByEmail(tokenEntity.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException(USER_NOT_FOUND));

        user.setEnabled(true);
        user.setRole(UserRole.USER);
        userRepository.save(user);

        tokenEntity.setUsed(true);
        tokenRepository.save(tokenEntity);
    }

    private void createAndStoreRegistrationToken(String email) {
        String token = UUID.randomUUID().toString();
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setValue(token);
        tokenEntity.setType(TokenType.REGISTRATION);
        tokenEntity.setUsed(false);
        tokenEntity.setExpirationDate(tokenEntity.generateExpirationDate());
        tokenEntity.setUserEmail(email);
        tokenRepository.save(tokenEntity);
    }

}

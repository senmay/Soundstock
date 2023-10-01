package com.soundstock.services;

import com.soundstock.enums.TokenType;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public void registerUser(UserDTO userDTO) {

        if (userRepository.existsByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())) {
            throw new EntityExistsException(ENTITY_EXISTS);
        }
        User user = userMapper.mapToUser(userDTO);
        userRepository.save(userMapper.mapToUserEntity(user));

        String token = UUID.randomUUID().toString();
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setValue(token);
        tokenEntity.setType(TokenType.REGISTRATION);
        tokenEntity.setUsed(false);
        tokenEntity.setExpirationDate(tokenEntity.generateExpirationDate());
        tokenEntity.setUserEmail(userDTO.getEmail());
        tokenRepository.save(tokenEntity);
    }

    public void confirmUser(String tokenValue) {
        TokenEntity tokenEntity = tokenRepository.findByValue(tokenValue)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        if (tokenEntity.getUsed()) {
            throw new IllegalStateException("Token has already been used");
        }
        UserEntity user = userRepository.findByEmail(tokenEntity.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setEnabled(true);
        userRepository.save(user);

        tokenEntity.setUsed(true);
        tokenRepository.save(tokenEntity);
    }
}

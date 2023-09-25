package com.soundstock.services;

import com.soundstock.dto.UserDTO;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public String registerUser(UserDTO userDTO) {

        if(userRepository.existsByUsernameOrEmail(userDTO.getUsername(), userDto.getEmail())){
            throw new EntityExistsException("Username or Email already exists");
        };
        //TODO mapowanie userdto do user
        String token = UUID.randomUUID().toString();
        //TODO encja tokena - wartosc tokena, typ tokena, czy token został juz uzyty, data wygasniecia, user email, metoda generujaca expiration date
        return null;
    }
}

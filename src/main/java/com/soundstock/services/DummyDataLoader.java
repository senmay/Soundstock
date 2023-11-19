package com.soundstock.services;

import com.soundstock.enums.UserRole;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DummyDataLoader {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadDummyData(){
        UserEntity admin = UserEntity.builder().id(1L).enabled(true).username("A").email("admin@wp.pl").role(UserRole.ADMIN).password(passwordEncoder.encode("password")).build();
        UserEntity user1 = UserEntity.builder().id(2L).enabled(true).username("U1").email("user1@wp.pl").role(UserRole.USER).password(passwordEncoder.encode("password1")).build();
        UserEntity user2 = UserEntity.builder().id(3L).enabled(true).username("U2").email("user2@wp.pl").role(UserRole.USER).password(passwordEncoder.encode("password2")).build();
        List<UserEntity> users = List.of(admin,user1,user2);
        userRepository.saveAll(users);
        System.out.println("Load users into database");
    }
}

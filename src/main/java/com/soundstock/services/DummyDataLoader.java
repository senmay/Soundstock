package com.soundstock.services;

import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapper;
import com.soundstock.mapper.UserMapperImpl;
import com.soundstock.model.User;
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
    private final UserMapper userMapper = new UserMapperImpl();
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadDummyData(){
        User admin = User.builder().id(1L).enabled(true).username("A").email("admin@wp.pl").role(UserRole.ADMIN).password(passwordEncoder.encode("password")).build();
        User user1 = User.builder().id(2L).enabled(true).username("U1").email("user1@wp.pl").role(UserRole.USER).password(passwordEncoder.encode("password1")).build();
        User user2 = User.builder().id(3L).enabled(true).username("U2").email("user2@wp.pl").role(UserRole.USER).password(passwordEncoder.encode("password2")).build();
        List<User> users = List.of(admin,user1,user2);
        for (User user: users){
            UserEntity userEntity = userMapper.mapToUserEntity(user);
            userRepository.save(userEntity);
        }
        System.out.println("Load users into database");

    }
}

package com.soundstock.services;
import com.soundstock.mapper.UserMapper;
import com.soundstock.model.User;
import com.soundstock.model.entity.TokenEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.TokenRepository;
import com.soundstock.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;
import jakarta.persistence.EntityExistsException;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;

    @Test
    public void testRegisterUserWithExistingUsernameOrEmail() {
        //given
        User user = User.builder().username("existingUsername").email("existingEmail@example.com").build();

        //when
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> {
            userService.registerUser(user);
        });

        //then
        verify(userRepository, times(1)).existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

    @Test
    public void testRegisterUserSuccessfully() {
        //given
        User user = User.builder().username("newUsername").email("newEmail@example.com").build();
        TokenEntity tokenEntity = new TokenEntity();
        UserEntity userEntity = new UserEntity();

        //when
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(false);
        when(userMapper.mapToUserEntity(user)).thenReturn(userEntity);

        userService.registerUser(user);

        //then
        verify(userRepository, times(1)).existsByUsernameOrEmail(user.getUsername(), user.getEmail());
        verify(userMapper, times(1)).mapToUserEntity(user);
        verify(userRepository, times(1)).save(userEntity);
        verify(tokenRepository, times(1)).save(any(TokenEntity.class));
    }
}

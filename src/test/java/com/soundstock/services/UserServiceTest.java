package com.soundstock.services;

import com.soundstock.mapper.UserMapper;
import com.soundstock.model.User;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.TokenEntity;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.TokenRepository;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

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
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterUserWithExistingUsernameOrEmail() {
        //given
        UserDTO userDTO = UserDTO.builder().username("existingUsername").email("existingEmail@example.com").build();
        User user = User.builder().username("existingUsername").email("existingEmail@example.com").build();

        //when
        when(userMapper.mapToUser(userDTO)).thenReturn(user);
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(true);

        assertThrows(EntityExistsException.class, () -> userService.registerUser(userDTO));

        //then
        verify(userRepository, times(1)).existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

    @Test
    public void shouldRegisterUserWhenUsernameAndEmailAreUnique() {
        UserDTO userDTO = UserDTO.builder().username("newUsername").password("password").email("newEmail@example.com").build();
        User user = User.builder().username("newUsername").email("newEmail@example.com").password("password").build();
        UserEntity userEntity = UserEntity.builder().username("newUsername").password("password").email("newEmail@example.com").build();

        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(false);
        when(userMapper.mapToUser(userDTO)).thenReturn(user);
        when(userMapper.mapToUserEntity(user)).thenReturn(userEntity);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        String token = userService.registerUser(userDTO);

        verify(userRepository, times(1)).existsByUsernameOrEmail(user.getUsername(), user.getEmail());
        verify(userMapper, times(1)).mapToUserEntity(user);
        verify(userRepository, times(1)).save(userEntity);
        verify(tokenRepository, times(1)).save(any(TokenEntity.class));
        Assertions.assertNotNull(token);
    }
}

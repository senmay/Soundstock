package com.soundstock.services;

import com.soundstock.mapper.UserMapper;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import com.soundstock.repository.TokenRepository;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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
        UserEntity user = UserEntity.builder().username("existingUsername").email("existingEmail@example.com").build();

        //when
        when(userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())).thenReturn(true);
        assertThrows(EntityExistsException.class, () -> userService.registerUser(userDTO, null));

        //then
        verify(userRepository, times(1)).existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

//    @Test
//    void shouldRegisterUserWhenUsernameAndEmailAreUnique() {
//        UserDTO userDTO = UserDTO.builder().username("newUsername").password("password").email("newEmail@example.com").build();
//        UserEntity userEntity = UserEntity.builder().username("newUsername").password("password").email("newEmail@example.com").build();
//
//        when(userRepository.existsByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())).thenReturn(false);
//        when(userMapper.mapToUserEntity(userDTO)).thenReturn(userEntity);
//        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
//
//        String token = userService.registerUser(userDTO, null);
//
//        verify(userRepository, times(1)).existsByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail());
//        verify(userMapper, times(1)).mapToUserEntity(userDTO);
//        verify(userRepository, times(1)).save(userEntity);
//        verify(tokenRepository, times(1)).save(any(TokenEntity.class));
//        Assertions.assertNotNull(token);
//    }
}

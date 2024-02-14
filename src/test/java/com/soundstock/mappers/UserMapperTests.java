package com.soundstock.mappers;

import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapperImpl;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserMapperTests {
    UserMapperImpl userMapper = new UserMapperImpl();

    @Test
    public void test_mapping_valid_userdto_to_userentity() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .password("password")
                .enabled(true)
                .role(UserRole.ADMIN)
                .build();

        // When
        UserEntity userEntity = userMapper.toEntity(userDTO);

        // Then
        assertEquals(userDTO.getId(), userEntity.getId());
        assertEquals(userDTO.getUsername(), userEntity.getUsername());
        assertEquals(userDTO.getEmail(), userEntity.getEmail());
        assertEquals(userDTO.getPassword(), userEntity.getPassword());
        assertEquals(userDTO.isEnabled(), userEntity.isEnabled());
        assertEquals(userDTO.getRole(), userEntity.getRole());
    }

    // Mapping a valid UserEntity to a UserDTO returns a UserDTO object with the same attributes.
    @Test
    public void test_mapping_valid_userentity_to_userdto() {
        // Given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .password("password")
                .enabled(true)
                .role(UserRole.ADMIN)
                .build();

        // When
        UserDTO userDTO = userMapper.toDTO(userEntity);

        // Then
        assertEquals(userEntity.getId(), userDTO.getId());
        assertEquals(userEntity.getUsername(), userDTO.getUsername());
        assertEquals(userEntity.getEmail(), userDTO.getEmail());
        assertEquals(userEntity.getPassword(), userDTO.getPassword());
        assertEquals(userEntity.isEnabled(), userDTO.isEnabled());
        assertEquals(userEntity.getRole(), userDTO.getRole());
    }

    // Mapping a list of valid UserEntity objects to a list of UserDTO objects returns a list of UserDTO objects with the same attributes.
    @Test
    public void test_mapping_list_of_valid_userentity_to_list_of_userdto() {
        // Given
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(UserEntity.builder()
                .id(1L)
                .username("john")
                .email("john@example.com")
                .password("password")
                .enabled(true)
                .role(UserRole.ADMIN)
                .build());
        userEntities.add(UserEntity.builder()
                .id(2L)
                .username("jane")
                .email("jane@example.com")
                .password("password")
                .enabled(true)
                .role(UserRole.USER)
                .build());

        // When
        List<UserDTO> userDTOs = userMapper.toDTO(userEntities);

        // Then
        assertEquals(userEntities.size(), userDTOs.size());

        for (int i = 0; i < userEntities.size(); i++) {
            UserEntity userEntity = userEntities.get(i);
            UserDTO userDTO = userDTOs.get(i);

            assertEquals(userEntity.getId(), userDTO.getId());
            assertEquals(userEntity.getUsername(), userDTO.getUsername());
            assertEquals(userEntity.getEmail(), userDTO.getEmail());
            assertEquals(userEntity.getPassword(), userDTO.getPassword());
            assertEquals(userEntity.isEnabled(), userDTO.isEnabled());
            assertEquals(userEntity.getRole(), userDTO.getRole());
        }
    }

    // Mapping an empty list of UserEntity objects to a list of UserDTO objects returns an empty list.
    @Test
    public void test_mapping_empty_list_of_userentity_to_list_of_userdto() {
        // Given
        List<UserEntity> userEntities = new ArrayList<>();

        // When
        List<UserDTO> userDTOs = userMapper.toDTO(userEntities);

        // Then
        assertTrue(userDTOs.isEmpty());
    }

    // Mapping a UserDTO object with empty string attributes returns a UserEntity object with empty string attributes.
    @Test
    public void test_mapping_userdto_with_empty_string_attributes_to_userentity_with_empty_string_attributes() {
        // Given
        UserDTO userDTO = UserDTO.builder()
                .id(1L)
                .username("")
                .email("")
                .password("")
                .enabled(true)
                .role(UserRole.ADMIN)
                .build();

        UserMapperImpl userMapper = new UserMapperImpl();

        // When
        UserEntity userEntity = userMapper.toEntity(userDTO);

        // Then
        assertEquals(userDTO.getId(), userEntity.getId());
        assertEquals(userDTO.getUsername(), userEntity.getUsername());
        assertEquals(userDTO.getEmail(), userEntity.getEmail());
        assertEquals(userDTO.getPassword(), userEntity.getPassword());
        assertEquals(userDTO.isEnabled(), userEntity.isEnabled());
        assertEquals(userDTO.getRole(), userEntity.getRole());
    }

    // Mapping a UserEntity object with empty string attributes returns a UserDTO object with empty string attributes.
    @Test
    public void test_mapping_userentity_with_empty_string_attributes_to_userdto_with_empty_string_attributes() {
        // Given
        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .username("")
                .email("")
                .password("")
                .enabled(true)
                .role(UserRole.ADMIN)
                .build();
        // When
        UserDTO userDTO = userMapper.toDTO(userEntity);

        // Then
        assertEquals(userEntity.getId(), userDTO.getId());
        assertEquals(userEntity.getUsername(), userDTO.getUsername());
        assertEquals(userEntity.getEmail(), userDTO.getEmail());
        assertEquals(userEntity.getPassword(), userDTO.getPassword());
        assertEquals(userEntity.isEnabled(), userDTO.isEnabled());
        assertEquals(userEntity.getRole(), userDTO.getRole());
    }
}



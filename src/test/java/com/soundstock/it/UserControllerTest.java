package com.soundstock.it;

import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapper;
import com.soundstock.mapper.UserMapperImpl;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
//@WebMvcTest(controllers = UserController.class)

class UserControllerTest {
    static Connection connection;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    UserMapper userMapper = new UserMapperImpl();
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("soundstock")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .withInitScript("init.sql");
    @DynamicPropertySource
    static void dynamicPropertyRegistry(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
    }

    @BeforeAll
    static void beforeAll() throws SQLException {
        connection = DriverManager.getConnection(postgres.getJdbcUrl(),postgres.getUsername(), postgres.getPassword());
        postgres.start();
    }

    @Test
    void testRegisterUser() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .username("dominik")
                .email("a@wp.pl")
                .password("password")
                .role(UserRole.USER)
                .id(1L)
                .build();

        //then
        mockMvc.perform(post("/user/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }
    @Test
    void should_throw_exception_because_username_exists() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username("dominik")
                .email("a@wp.pl")
                .password("password")
                .role(UserRole.USER)
                .id(1L)
                .build();
//        String sql = "INSERT INTO users (id, is_enabled, email, username) VALUES (1, false, '" + userDTO.getEmail() + "', '" + userDTO.getUsername() + "');";
//        PreparedStatement preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.execute();
        userRepository.save(userMapper.mapToUserEntity(userDTO));

        mockMvc.perform(post("/user/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testConfirmUser() throws Exception {
        // given
        String token = "someToken";

        // when & then
        mockMvc.perform(post("/user/v1/confirm")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

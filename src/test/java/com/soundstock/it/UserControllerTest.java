package com.soundstock.it;

import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapper;
import com.soundstock.mapper.UserMapperImpl;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
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
    static void dynamicPropertyRegistry(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> postgres.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
    }

    @BeforeAll
    static void beforeAll() throws SQLException {
        connection = DriverManager.getConnection(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword());
        postgres.start();
    }

    @Test
    void testRegisterUser() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .username("test1")
                .email("test1@wp.pl")
                .password("password")
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
                .build();

        userRepository.save(userMapper.mapToUserEntity(userDTO));

        mockMvc.perform(post("/user/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void testRegistrationAndConfirmationFlow() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username("test2")
                .email("test2@wp.pl")
                .password("password")
                .build();

        MvcResult registrationResult = mockMvc.perform(post("/user/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        // Pobranie tokena z odpowiedzi rejestracji
        String token = registrationResult.getResponse().getContentAsString();

        // Wykonanie żądania potwierdzenia z użytym tokenem
        mockMvc.perform(post("/user/v1/confirm")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testLogin() throws Exception {
        UserDTO userDTO = UserDTO.builder()
                .username("test3")
                .email("test3@wp.pl")
                .password("password")
                .build();

        MvcResult registrationResult = mockMvc.perform(post("/user/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        // Pobranie tokena z odpowiedzi rejestracji
        String token = registrationResult.getResponse().getContentAsString();

        // Wykonanie żądania potwierdzenia z użytym tokenem
        MvcResult confirmationResult = mockMvc.perform(post("/user/v1/confirm")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginWithWrongCredentials() throws Exception {
        // Użytkownik z błędnymi danymi
        UserDTO wrongUserDTO = UserDTO.builder()
                .email("wrongEmail")
                .password("wrongPassword")
                .username("wrongUsername")
                .build();

        // Próba logowania z błędnymi danymi
        mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(wrongUserDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllUsersAsAdmin() throws Exception {
        UserDTO adminLogin = UserDTO.builder()
                .username("A")
                .password("password")
                .email("admin@wp.pl")
                .build();

        // Logowanie jako admin i pobranie tokenu JWT
        MvcResult loginResult = mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminLogin)))
                .andExpect(status().isOk())
                .andReturn();

        String adminToken = loginResult.getResponse().getHeader("Access_token");
        System.out.println(adminToken);

        // Wykonanie żądania do endpointu z tokenem JWT w nagłówku
        mockMvc.perform(get("/user/v1/userlist")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].username", Matchers.equalTo("A")));
    }

    @Test
    void shouldRefreshTokenSuccessfully() throws Exception {
        UserDTO adminLogin = UserDTO.builder()
                .username("A")
                .password("password")
                .email("admin@wp.pl")
                .build();

        // Logowanie jako admin i pobranie tokenow
        MvcResult loginResult = mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminLogin)))
                .andExpect(status().isOk())
                .andReturn();
        Thread.sleep(500);

        String refreshToken = loginResult.getResponse().getHeader("Refresh_token");

        mockMvc.perform(post("/user/v1/refresh")
                        .header("Refresh_token", refreshToken))
                .andExpect(status().isOk())
                .andExpect(header().exists("Access_token"));
    }
}


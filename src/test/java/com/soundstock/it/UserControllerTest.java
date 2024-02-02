package com.soundstock.it;

import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapper;
import com.soundstock.mapper.UserMapperImpl;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {
    static Connection connection;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("soundstock")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .withInitScript("init.sql");
    @Autowired
    UserRepository userRepository;
    UserMapper userMapper = new UserMapperImpl();
    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityExistsException))
                .andExpect(result -> assertEquals("Username or Email already exists", result.getResolvedException().getMessage()));
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
                .andExpect(status().isBadRequest());/*todo tutaj nie dostajesz 404.
                W konsoli widzę log "user not found for username", a pojawia się on przed rzuceniem ObjectNotFound.
                Dla ObjectNotFound w GlobalExceptionHandler jest dodawane do response bad request, a nie not found :)
   */
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

        String adminToken = loginResult.getResponse().getHeader("JWT_token"); //todo tutaj zwykły bug. sprawdź co nadajesz w loginie do nagłówków
        System.out.println(adminToken);

        // Wykonanie żądania do endpointu z tokenem JWT w nagłówku
        mockMvc.perform(get("/user/v1/userlist")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[0].username", Matchers.equalTo("A")));
    }
}


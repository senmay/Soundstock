package com.soundstock.it;

import com.soundstock.TestSecurityConfig;
import com.soundstock.controller.UserController;
import com.soundstock.enums.UserRole;
import com.soundstock.mapper.UserMapper;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@Import(TestSecurityConfig.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    UserMapper userMapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("soundstock")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .withInitScript("init.sql");

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @Test
    public void testRegisterUser() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .username("dominik")
                .email("a@wp.pl")
                .password("password")
                .role(UserRole.USER)
                .id(1L)
                .build();

        when(userService.registerUser(userMapper.mapToUser(userDTO))).thenReturn("token");

        //then
        mockMvc.perform(post("/user/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testConfirmUser() throws Exception {
        // given
        String token = "someToken";

        doNothing().when(userService).confirmUser(token);

        // when & then
        mockMvc.perform(post("/user/v1/confirm")
                        .param("token", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}

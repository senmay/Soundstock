package com.soundstock.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.repository.ArtistRepository;
import com.soundstock.repository.SongRepository;
import com.soundstock.testdata.ResourceFactory;
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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class ArtistControllerTest extends ResourceFactory {
    static Connection connection;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ArtistRepository artistRepository;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("soundstock")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .withInitScript("init.sql");
    @Autowired
    private SongRepository songRepository;

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
    void should_add_artist_with_admin_credentials() throws Exception {
        ArtistDTO artistDTO = provideArtistDTO();

        mockMvc.perform(post("/artist/v1/add")
                        .header("Authorization", "Bearer " + obtainAdminAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(artistDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_throw_403_artist_with_user_credentials() throws Exception {
        ArtistDTO artistDTO = provideArtistDTO();

        mockMvc.perform(post("/artist/v1/add")
                        .header("Authorization", "Bearer " + obtainUserAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(artistDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_get_all_artists_with_user_credentials() throws Exception {
        List<ArtistDTO> artistDTOList = provideArtistDTOList(3);
        String adminAccessToken = obtainAdminAccessToken();
        for (ArtistDTO artistDTO : artistDTOList) {
            mockMvc.perform(post("/artist/v1/add")
                            .header("Authorization", "Bearer " + adminAccessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(artistDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Artist added"));
        }

        mockMvc.perform(get("/artist/v1/getAll")
                        .header("Authorization", "Bearer " + obtainUserAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$[2].id", Matchers.equalTo(3)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(artistDTOList.get(0).getName())))
                .andExpect(jsonPath("$[1].name", Matchers.equalTo(artistDTOList.get(1).getName())))
                .andExpect(jsonPath("$[2].name", Matchers.equalTo(artistDTOList.get(2).getName())));

    }

    private String obtainAdminAccessToken() throws Exception {
        // Admin Credentials are already in database
        UserDTO adminLogin = UserDTO.builder()
                .username("A")
                .password("password")
                .email("admin@wp.pl")
                .build();
        MvcResult loginResult = mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(adminLogin)))
                .andExpect(status().isOk())
                .andReturn();

        return loginResult.getResponse().getHeader("Access_token");
    }

    private String obtainUserAccessToken() throws Exception {
        // User Credentials are already in database
        UserDTO userLogin = UserDTO.builder()
                .username("U1")
                .password("password1")
                .email("admin@wp.pl")
                .build();

        MvcResult loginResult = mockMvc.perform(post("/user/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isOk())
                .andReturn();

        return loginResult.getResponse().getHeader("Access_token");
    }

}

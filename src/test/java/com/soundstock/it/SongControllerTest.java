package com.soundstock.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.dto.UserDTO;
import com.soundstock.model.entity.SongEntity;
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
class SongControllerTest extends ResourceFactory {
    static Connection connection;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("soundstock")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .withInitScript("init.sql");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    SongRepository songRepository;

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
    void should_add_song_with_null_album_with_admin_credentials() throws Exception {
        SongEntity song = provideSongEntity();
        song.setAlbum(null);

        mockMvc.perform(post("/song/v1/add")
                        .header("Authorization", "Bearer " + obtainAdminAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(song)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_throw_403_when_add_song_with_user_credentials() throws Exception {
        SongEntity song = provideSongEntity();
        song.setAlbum(null);

        mockMvc.perform(post("/song/v1/add")
                        .header("Authorization", "Bearer " + obtainUserAccessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(song)))
                .andExpect(status().isForbidden());
    }

    @Test
    void should_get_all_songs_with_user_credentials() throws Exception {
        //Adding songs with admin access token
        List<SongDTO> songDTOS = provideRandomSongList(3);
        songRepository.findAll();
        String adminAccessToken = obtainAdminAccessToken();
        for (SongDTO songDTO : songDTOS) {
            mockMvc.perform(post("/song/v1/add")
                            .header("Authorization", "Bearer " + adminAccessToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(new ObjectMapper().writeValueAsString(songDTO)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string("Song added"));
        }

        mockMvc.perform(get("/song/v1/getAll")
                        .header("Authorization", "Bearer " + obtainUserAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$[2].id", Matchers.equalTo(3)))
                .andExpect(jsonPath("$[0].title", Matchers.equalTo(songDTOS.get(0).getTitle())))
                .andExpect(jsonPath("$[1].title", Matchers.equalTo(songDTOS.get(1).getTitle())))
                .andExpect(jsonPath("$[2].title", Matchers.equalTo(songDTOS.get(2).getTitle())));
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

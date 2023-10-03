package com.soundstock.it;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class UserControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("soundstock")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(5432)
            .withInitScript("init.sql");

    @BeforeAll
    static void beforeAll(){
        postgres.start();
    }

    //TODO WebMockMvc

}

package com.soundstock;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@OpenAPIDefinition(info =@Info(title = "Soundstock API"))
public class SoundstockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoundstockApplication.class, args);
    }

}

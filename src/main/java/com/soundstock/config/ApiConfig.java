package com.soundstock.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "api")
@Configuration
@Data

public class ApiConfig {
    @Value("${coingecko.apikey}")
    private String coingeckoApikey;
    @Value("${lastfm.apikey}")
    private String lastfmApikey;
    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}

package com.soundstock.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "api")
@Configuration
@Data

public class ApiConfig {
    @Value("${coingecko.url}")
    private String geckoUrl;
    @Value("${coingecko.apikey}")
    private String geckoKey;
    @Value("${lastfm.url}")
    private String lastFmUrl;
    @Value("${lastfm.apikey}")
    private String lastFmKey;

}

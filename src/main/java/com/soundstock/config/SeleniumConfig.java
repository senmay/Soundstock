package com.soundstock.config;

import com.soundstock.util.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class SeleniumConfig {
    @Bean
    public WebDriver webDriver() {
        return WebDriverFactory.createDriver();
    }
}


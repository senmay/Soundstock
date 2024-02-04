package com.soundstock.it;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SeleniumParseScrapTest {
    @Test
    public void testPageSourceIsNotEmpty() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://www.coingecko.com/en/all-cryptocurrencies");
        String pageSource = driver.getPageSource();
        driver.quit();
        System.out.println(pageSource);

        // Sprawdzamy, czy źródło strony nie jest puste
        assertFalse(pageSource.isEmpty(), "Źródło strony nie powinno być puste");
    }
}

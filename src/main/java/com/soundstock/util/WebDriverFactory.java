package com.soundstock.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {
    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        options.addArguments("--headless");
        options.addArguments("window-size=1920,1080");
        return new ChromeDriver(options);
    }
}


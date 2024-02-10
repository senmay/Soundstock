package com.soundstock.services.helpers;

import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SeleniumService {
    private final WebDriver driver;
    private final WebDriverWait wait;
    public SeleniumService(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    public List<CoingeckoStockDTO> scrapCoinsFromSelenium(Integer size) {
        List<CoingeckoStockDTO> coins = new ArrayList<>();
        try {
            navigateToCoinGecko();
            //acceptCookies();
            scrollAndLoadAllCoins(size);
            coins = extractCoinData();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return coins;
    }

    private void navigateToCoinGecko() {
        driver.get("https://www.coingecko.com/en/all-cryptocurrencies");
    }

    private void scrollAndLoadAllCoins(Integer size) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long previousHeight = (long) js.executeScript("return document.body.scrollHeight");

        for (int i = 0; i < size; i++) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            TimeUnit.SECONDS.sleep(2); // Oczekiwanie na załadowanie strony
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn.btn-primary.btn-sm.mt-3"))).click();
            TimeUnit.SECONDS.sleep(2); // Oczekiwanie na załadowanie nowych danych
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == previousHeight) {
                break;
            }
            previousHeight = newHeight;
        }
    }
    private List<CoingeckoStockDTO> extractCoinData() {
        List<WebElement> rows = getCoingeckoWebElements();
        List<CoingeckoStockDTO> coins = new ArrayList<>();
        for (WebElement row : rows) {
            coins.add(createCoinDtoFromWebElement(row));
        }
        return coins;
    }
    private List<WebElement> getCoingeckoWebElements() {
        return driver.findElements(By.cssSelector("tbody[data-target='all-coins.tablebody'] > tr"));
    }

    private CoingeckoStockDTO createCoinDtoFromWebElement(WebElement row) {
        CoingeckoStockDTO coin = new CoingeckoStockDTO();
        coin.setName(safeGetText(row, ".tw-text-blue-500.tw-font-bold.lg\\:tw-block.tw-hidden"));
        coin.setSymbol(safeGetText(row, ".tw-hidden.d-lg-inline.font-normal.text-3xs.mt-1"));
        coin.setPrice(cleanDataAndConvertToBigDecimal(safeGetText(row, "span[data-target='price.price']")));
        coin.setPrice1h(safeGetText(row, ".td-change1h > span"));
        coin.setPrice24h(safeGetText(row, ".td-change24h > span"));
        coin.setPrice7d(safeGetText(row, ".td-change7d > span"));
        coin.setPrice30d(safeGetText(row, ".td-change30d > span"));
        coin.setCirculatingSupply(cleanDataAndConvertToBigDecimal(safeGetText(row, ".td-circulating_supply > div")));
        coin.setTotalSupply(safeGetText(row, ".td-total_supply > div"));
        coin.setMarketCap(cleanDataAndConvertToBigDecimal(safeGetText(row, ".td-market_cap.cap .cap-price.text-right .no-wrap")));
        return coin;
    }
    private static String safeGetText(WebElement row, String selector) {
        try {
            return row.findElement(By.cssSelector(selector)).getText();
        } catch (Exception e) {
            return "-";
        }
    }
    private BigDecimal cleanDataAndConvertToBigDecimal(String data) {
        if (data == null || data.isEmpty() || data.equals("-")) {
            log.error("Ciąg jest pusty lub nieprawidłowy.");
            return BigDecimal.ZERO; // Zwraca BigDecimal.ZERO w przypadku pustego ciągu
        } else {
            try {
                String cleanedData = data.replaceAll("[^\\d.]", "");
                return new BigDecimal(cleanedData);
            } catch (NumberFormatException e) {
                log.error("Błąd podczas konwersji '{}' na BigDecimal", data, e);
                return BigDecimal.ZERO; // Zwraca BigDecimal.ZERO w przypadku błędu konwersji
            }
        }
    }
}

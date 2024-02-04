package com.soundstock.services;

import com.soundstock.model.dto.CoingeckoStockDTO;
import com.soundstock.model.entity.CoingeckoStockEntity;
import com.soundstock.repository.CoingeckoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrappingService {
    private final CoingeckoRepository coingeckoStockRepository;
    String csvFilePath = "src/main/resources/static/coins.csv";

    public List<CoingeckoStockDTO> scrapCoinsFromSelenium() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        WebDriver driver = new ChromeDriver(options);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<CoingeckoStockDTO> coins = new ArrayList<>();
        try {
            driver.get("https://www.coingecko.com/en/all-cryptocurrencies");

            // Oczekiwanie na załadowanie strony nie jest pokazane, ale zalecane jest użycie WebDriverWait tutaj.
            long previousHeight = (long) js.executeScript("return document.body.scrollHeight");
            for (int i = 0; i < 2; i++) {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                TimeUnit.SECONDS.sleep(2); // Oczekiwanie na załadowanie strony
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Show More"))).click();
                TimeUnit.SECONDS.sleep(2); // Oczekiwanie na załadowanie nowych danych
                long newHeight = (long) js.executeScript("return document.body.scrollHeight");
                if (newHeight == previousHeight) {
                    break;
                }
                previousHeight = newHeight;
            }
            // Znajdowanie wierszy kryptowalut
            List<WebElement> rows = driver.findElements(By.cssSelector("tbody[data-target='all-coins.tablebody'] > tr"));
            for (WebElement row : rows) {
                CoingeckoStockDTO coin = new CoingeckoStockDTO();
                coin.setName(safeGetText(row, ".tw-text-blue-500.tw-font-bold.lg\\:tw-block.tw-hidden"));
                coin.setSymbol(safeGetText(row, ".tw-hidden.d-lg-inline.font-normal.text-3xs.mt-1"));
                coin.setPrice(new BigDecimal(safeGetText(row, "span[data-target='price.price']").replaceAll("[^\\d.]", "")));
                coin.setPrice1h(safeGetText(row, ".td-change1h > span"));
                coin.setPrice24h(safeGetText(row, ".td-change24h > span"));
                coin.setPrice7d(safeGetText(row, ".td-change7d > span"));
                coin.setPrice30d(safeGetText(row, ".td-change30d > span"));
                coin.setTotalVolume24h(new BigDecimal(safeGetText(row, ".td-total_volume > span").replaceAll("[^\\d.]", "")));
                coin.setCirculatingSupply(new BigDecimal(safeGetText(row, ".td-circulating_supply > div").replaceAll("[^\\d.]", "")));
                coin.setTotalSupply(safeGetText(row, ".td-total_supply > div"));
                coin.setMarketCap(new BigDecimal(safeGetText(row, ".td-market_cap.cap .cap-price.text-right .no-wrap").replaceAll("[^\\d.]", "")));

                coins.add(coin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return coins;
    }

    public void saveCoinsToCsv(List<CoingeckoStockDTO> coins) {
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // Nagłówek CSV
            writer.append("Name,Symbol,Price,Price1h,Price24h,Price7d,Price30d,TotalVolume24h,CirculatingSupply,TotalSupply,MarketCap\n");

            // Iteracja przez listę obiektów CoingeckoStockDTO i zapisywanie w pliku
            for (CoingeckoStockDTO coin : coins) {
                writer.append(coin.getName()).append(",")
                        .append(coin.getSymbol()).append(",")
                        .append(coin.getPrice().toString()).append(",")
                        .append(coin.getPrice1h()).append(",")
                        .append(coin.getPrice24h()).append(",")
                        .append(coin.getPrice7d()).append(",")
                        .append(coin.getPrice30d()).append(",")
                        .append(coin.getTotalVolume24h().toString()).append(",")
                        .append(coin.getCirculatingSupply().toString()).append(",")
                        .append(coin.getTotalSupply()).append(",")
                        .append(coin.getMarketCap().toString()).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<CoingeckoStockDTO> saveCoinsToCsvAndReturnData() {
        List<CoingeckoStockDTO> coins = scrapCoinsFromSelenium();
        saveCoinsToCsv(coins);
        return coins;
    }

    @Transactional
    public void importCsvToDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(csvFilePath).toFile()))) {
            String line;
            br.readLine(); // Pomijamy nagłówek CSV
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                CoingeckoStockEntity stock = CoingeckoStockEntity.builder()
                        .name(data[0])
                        .symbol(data[1])
                        .price(new BigDecimal(data[2]))
                        .price1h(data[3])
                        .price24h(data[4])
                        .price7d(data[5])
                        .price30d(data[6])
                        .totalVolume24h(new BigDecimal(data[7].replaceAll("[^\\d.]", "")))
                        .circulatingSupply(new BigDecimal(data[8].replaceAll("[^\\d.]", "")))
                        .totalSupply(data[9])
                        .marketCap(new BigDecimal(data[10].replaceAll("[^\\d.]", "")))
                        .build();
                coingeckoStockRepository.save(stock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String safeGetText(WebElement row, String selector) {
        try {
            return row.findElement(By.cssSelector(selector)).getText();
        } catch (Exception e) {
            return "-";
        }
    }
}

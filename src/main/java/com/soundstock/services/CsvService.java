package com.soundstock.services;

import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.entity.CoingeckoStockEntity;
import com.soundstock.repository.CoingeckoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {
    private final CoingeckoRepository coingeckoStockRepository;
    String csvFilePath = "src/main/resources/static/coins.csv";
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
}

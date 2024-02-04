package com.soundstock.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.soundstock.config.ApiConfig;
import com.soundstock.model.dto.CoingeckoStockDTO;
import com.soundstock.services.ApiService;
import com.soundstock.services.ScrappingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
@Slf4j
public class ApiController {
    private final ApiService apiService;
    private final ApiConfig apiConfig;
    private final ScrappingService scrapperService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CoingeckoStockDTO> getCoinsFromCoingeckoApi(){
        log.info("Pobieranie listy kryptowalut");
        return apiService.fetchCoins();
    }
    @GetMapping("/coins")
    @ResponseStatus(HttpStatus.OK)
    public List<CoingeckoStockDTO> getCoinsSelenium(){
        log.info("Pobieranie listy kryptowalut");
        return scrapperService.scrapCoinsFromSelenium();
    }
    @GetMapping("/coins2")
    @ResponseStatus(HttpStatus.OK)
    public List<CoingeckoStockDTO> saveCoinsToCsv(){
        log.info("Zapisywanie listy kryptowalut do pliku");
        return scrapperService.saveCoinsToCsvAndReturnData();
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void importCoinsToDatabase(){
        log.info("Importowanie danych");
        scrapperService.importCsvToDatabase();
    }
    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<JsonNode> getMostPopularSongs(){
        log.info("Pobieranie listy piosenek");
        return apiService.getMostPopularSongs();
    }
}

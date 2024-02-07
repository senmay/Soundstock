package com.soundstock.controller.api.coingecko;

import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.services.ApiService;
import com.soundstock.services.helpers.ApiCoordinatorService;
import com.soundstock.services.CsvService;
import com.soundstock.services.helpers.SeleniumService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("coin/v1")
@Slf4j
public class CoingeckoController {
    private final ApiService apiService;
    private final ApiCoordinatorService scrapperService;
    private final CsvService csvService;
    private final SeleniumService seleniumService;
    @GetMapping("/coingecko")
    @ResponseStatus(HttpStatus.OK)
    public List<CoingeckoStockDTO> getCoinsFromCoingeckoApi(){
        log.info("Pobieranie listy kryptowalut");
        return apiService.fetchCoins();
    }
    @GetMapping("/coins")
    @ResponseStatus(HttpStatus.OK)
    public List<CoingeckoStockDTO> getCoinsSelenium(@Param("size") Integer size){
        log.info("Pobieranie listy kryptowalut");
        return seleniumService.scrapCoinsFromSelenium(size);
    }
    @GetMapping("/coins2")
    @ResponseStatus(HttpStatus.OK)
    public void saveCoinsToCsv(@Param("size") Integer size){
        log.info("Zapisywanie listy kryptowalut do pliku");
        scrapperService.fetchAndSaveCoinDataToCsv(size);
    }

    @GetMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void importCoinsToDatabase(){
        log.info("Importowanie danych");
        csvService.importCsvToDatabase();
    }
}

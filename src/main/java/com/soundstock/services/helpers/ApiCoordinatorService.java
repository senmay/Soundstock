package com.soundstock.services.helpers;

import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.dto.api.lastfm.Track;
import com.soundstock.services.ApiService;
import com.soundstock.services.CsvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiCoordinatorService {

    private final SeleniumService seleniumService;
    private final CsvService csvService;
    private final ApiService apiService;

    public void fetchAndSaveCoinDataToCsv(Integer size) {
        // Pobieranie danych o kryptowalutach
        List<CoingeckoStockDTO> coins = seleniumService.scrapCoinsFromSelenium(size);

        // Zapisywanie danych do pliku CSV
        csvService.saveCoinsToCsv(coins);
    }
    public void fetchAndSaveTrackDataToDatabase(){
        List<Track> mostPopularSongs = apiService.getMostPopularSongs();
        apiService.saveTracksAsEntity(mostPopularSongs);
    }
}

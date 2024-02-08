package com.soundstock.services.helpers;

import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.dto.api.lastfm.Track;
import com.soundstock.services.CsvService;
import com.soundstock.services.api.LastFmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiCoordinatorService {

    private final SeleniumService seleniumService;
    private final CsvService csvService;
    private final LastFmService lastFmService;

    public void fetchAndSaveCoinDataToCsv(Integer size) {
        // Pobieranie danych o kryptowalutach
        List<CoingeckoStockDTO> coins = seleniumService.scrapCoinsFromSelenium(size);

        // Zapisywanie danych do pliku CSV
        csvService.saveCoinsToCsv(coins);
    }
    public void fetchAndSaveTrackDataToDatabase(){
        List<Track> mostPopularSongs = lastFmService.getMostPopularSongs();
        lastFmService.saveTracksAsEntity(mostPopularSongs);
    }
}

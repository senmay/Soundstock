package com.soundstock.services.helpers;

import com.soundstock.mapper.TrackSpotifyMapper;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.dto.api.lastfm.TrackLastFm;
import com.soundstock.model.dto.api.spotify.ItemSpotify;
import com.soundstock.services.AlbumService;
import com.soundstock.services.ArtistService;
import com.soundstock.services.CsvService;
import com.soundstock.services.TrackService;
import com.soundstock.services.api.LastFmService;
import com.soundstock.services.api.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiCoordinatorService {

    private final SeleniumService seleniumService;
    private final CsvService csvService;
    private final LastFmService lastFmService;
    private final SpotifyService spotifyService;
    private final TrackSpotifyMapper trackSpotifyMapper;
    private final TrackService trackService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    public void fetchAndSaveCoinDataToCsv(Integer size) {
        // Pobieranie danych o kryptowalutach
        List<CoingeckoStockDTO> coins = seleniumService.scrapCoinsFromSelenium(size);

        // Zapisywanie danych do pliku CSV
        csvService.saveCoinsToCsv(coins);
    }
    public void fetchAndSaveTrackDataToDatabase(){
        List<TrackLastFm> mostPopularSongs = lastFmService.getMostPopularSongs();
        lastFmService.saveTracksAsEntity(mostPopularSongs);
    }
    public void fetchAndSaveSpotifyTrackToDatabase(Integer year){
        List<ItemSpotify> mostPopularSongs = spotifyService.getMostPopularSongsFromYear(year);
        mostPopularSongs.stream()
                .map(ItemSpotify::getTrack)
                .map(trackSpotifyMapper::mapTrackSpotifyToTrackDTO)
                .forEach(trackService::addTrack);
    }
}

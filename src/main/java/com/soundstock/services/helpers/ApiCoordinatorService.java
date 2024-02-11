package com.soundstock.services.helpers;

import com.soundstock.mapper.PlaylistMapper;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.mapper.TrackSpotifyMapper;
import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.dto.api.lastfm.TrackLastFm;
import com.soundstock.model.dto.api.spotify.ItemSpotify;
import com.soundstock.model.dto.api.spotify.PlaylistSpotify;
import com.soundstock.model.entity.PlaylistEntity;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.services.*;
import com.soundstock.services.api.LastFmService;
import com.soundstock.services.api.SpotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiCoordinatorService {

    private final SeleniumService seleniumService;
    private final CsvService csvService;
    private final LastFmService lastFmService;
    private final SpotifyService spotifyService;
    private final TrackSpotifyMapper trackSpotifyMapper;
    private final PlaylistMapper playlistMapper;
    private final PlaylistService playlistService;
    private final TrackMapper trackMapper;
    private final TrackService trackService;

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
    public void fetchAndSaveSpotifyTrackToDatabase(Integer year) {
        PlaylistSpotify mostPopularSongsFromYear = spotifyService.getMostPopularSongsFromYear(year);
        String playlistName = mostPopularSongsFromYear.getName();

        List<ItemSpotify> mostPopularSongs = mostPopularSongsFromYear.getTracks().getItems();
        List<TrackDTO> trackDTOs = mostPopularSongs.stream()
                .map(ItemSpotify::getTrack)
                .map(trackSpotifyMapper::mapTrackSpotifyToTrackDTO)
                .toList();
        Optional<PlaylistEntity> existingPlaylistOpt = playlistService.findByName(playlistName);

        if (existingPlaylistOpt.isPresent()) {
            for (TrackDTO track : trackDTOs) {
                trackService.addTrack(track);
            }
        } else {
            // Jeśli playlista nie istnieje, utwórz nową i dodaj do niej utwory
            PlaylistDTO newPlaylist = PlaylistDTO.builder()
                    .name(playlistName)
                    .build();
            for (TrackDTO track : trackDTOs) {
                newPlaylist.addTrack(track);
                trackService.addTrack(track, newPlaylist);
            }
            log.info("Created new playlist with name: {} and added tracks", playlistName);
        }
    }
}

package com.soundstock.services.helpers;

import com.soundstock.mapper.*;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.dto.api.lastfm.TrackLastFm;
import com.soundstock.model.dto.api.spotify.ItemSpotify;
import com.soundstock.model.dto.api.spotify.PlaylistResponse;
import com.soundstock.model.dto.api.spotify.TrackSpotify;
import com.soundstock.model.entity.*;
import com.soundstock.repository.TrackPopularityHistoryRepository;
import com.soundstock.services.*;
import com.soundstock.services.api.LastFmService;
import com.soundstock.services.api.SpotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiCoordinatorService {

    private final SeleniumService seleniumService;
    private final CsvService csvService;
    private final LastFmService lastFmService;
    private final SpotifyService spotifyService;
    private final TrackSpotifyMapper trackSpotifyMapper;
    private final TrackPopularityHistoryRepository trackPopularityHistoryRepository;
    private final PlaylistService playlistService;
    private final ArtistService artistService;
    private final TrackService trackService;
    private final AlbumService albumService;
    private final AlbumMapper albumMapper = new AlbumMapperImpl();
    private final ArtistMapper artistMapper = new ArtistMapperImpl();

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
    public void fetchAndSaveSpotifyPlaylistTracksToDatabase(Integer year) {
        PlaylistResponse playlist = spotifyService.getSpotifyTopTracksByYear(year);
        PlaylistEntity playlistEntity = playlistService.ensurePlaylistExists(playlist.getName());
        List<TrackDTO> trackDTOs = getTracksFromSpotify(playlist);
        List<AlbumEntity> albums = getAlbumsFromSpotify(playlist);
        List<ArtistEntity> artists = getArtistsFromSpotify(playlist);
        saveAlbums(albums);
        saveArtists(artists);
        saveTracks(trackDTOs, playlistEntity);
        saveTrackPopularityHistory(trackDTOs);
    }

    private void saveTracks(List<TrackDTO> trackDTOs, PlaylistEntity playlistEntity) {
        trackDTOs.forEach(trackDTO -> trackService.addTrack(trackDTO, playlistEntity));
        log.info("Playlist updated with name: {} and added tracks", playlistEntity.getName());
    }
    private void saveAlbums(List<AlbumEntity> albums) {
        albums.forEach(albumService::addAlbum);
        log.info("Albums added to database");
    }
    private void saveArtists(List<ArtistEntity> artists){
        artists.forEach(artistService::addArtist);
        log.info("Artists added to database");
    }
    private List<TrackDTO> getTracksFromSpotify(PlaylistResponse playlistResponse) {
        List<ItemSpotify> mostPopularSongs = playlistResponse.getTracks().getItems();
        return mostPopularSongs.stream()
                .map(ItemSpotify::getTrack)
                .map(trackSpotifyMapper::toDTO)
                .toList();
    }

    private void saveTrackPopularityHistory(List<TrackDTO> trackDTOs) {
        trackDTOs.forEach(trackDTO -> {
            TrackEntity trackEntity = trackService.getTrackBySpotifyId(trackDTO.getSpotifyId());
            if (trackEntity != null) {
                TrackPopularityHistory history = TrackPopularityHistory.builder()
                        .track(trackEntity)
                        .popularity(trackDTO.getPopularity())
                        .timestamp(LocalDateTime.now())
                        .spotifyId(trackDTO.getSpotifyId())
                        .build();
                trackPopularityHistoryRepository.save(history);
            }
        });
        log.info("Track popularity history updated");
    }
    private List<AlbumEntity> getAlbumsFromSpotify(PlaylistResponse playlistResponse) {
        List<ItemSpotify> mostPopularSongs = playlistResponse.getTracks().getItems();
        return mostPopularSongs.stream()
                .map(ItemSpotify::getTrack)
                .map(TrackSpotify::getAlbum)
                .map(albumMapper::toEntity)
                .toList();
    }
    private List<ArtistEntity> getArtistsFromSpotify(PlaylistResponse playlistResponse) {
        List<ItemSpotify> mostPopularSongs = playlistResponse.getTracks().getItems();
        return mostPopularSongs.stream()
                .map(ItemSpotify::getTrack)
                .map(TrackSpotify::getArtists)
                .flatMap(List::stream)
                .map(artistMapper::toEntity)
                .toList();
    }
}

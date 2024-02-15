package com.soundstock.services.api;

import com.soundstock.config.ApiConfig;
import com.soundstock.model.dto.api.spotify.TrackSpotify;
import com.soundstock.model.dto.api.spotify.PlaylistResponse;
import com.soundstock.model.dto.api.spotify.TracksResponse;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.model.entity.TrackPopularityHistory;
import com.soundstock.repository.TrackPopularityHistoryRepository;
import com.soundstock.repository.TrackRepository;
import com.soundstock.services.helpers.HttpClientService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;

import static com.soundstock.enums.ApiEndpointsConstants.YEAR_TO_SPOTIFY_URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotifyService {
    private final TrackRepository trackRepository;
    private final HttpClientService httpClientService;
    private final TrackPopularityHistoryRepository trackPopularityHistoryRepository;
    private final ApiConfig apiConfig;

    public PlaylistResponse getSpotifyTopTracksByYear(Integer year) {
        String url = YEAR_TO_SPOTIFY_URL.getOrDefault(year, "bad url");
        HttpRequest request = buildRequestForTopTracksByYear(url);
        log.debug("Request URI: {}", request.uri());
        return httpClientService.sendRequest(request, PlaylistResponse.class);
    }

    public void updateAllTracksPopularity() {
        List<String> allTracksSpotifyIds = getAllTracksSpotifyIds();
        fetchAndProcessTracksData(splitIdsIntoGroups(allTracksSpotifyIds));
    }
    public List<String> getAllTracksSpotifyIds() {
        return trackPopularityHistoryRepository.findAll().stream()
                .map(TrackPopularityHistory::getSpotifyId)
                .toList();
    }
    // Splitting the list of ids into groups of 25, because Spotify API allows to fetch data for 25 tracks at once
    private List<List<String>> splitIdsIntoGroups(List<String> ids) {
        return List.of(ids.subList(0, 25), ids.subList(25, ids.size()));
    }
    public void fetchAndProcessTracksData(List<List<String>> groupOfIds){
        for (List<String> ids : groupOfIds) {
            String joinedIds = String.join(",", ids);
            HttpRequest request = buildRequestForTrackPopularity(joinedIds);
            TracksResponse tracksResponse = httpClientService.sendRequest(request, TracksResponse.class);
            saveNewTrackPopularityHistory(tracksResponse);
        }
    }
    public void saveNewTrackPopularityHistory(TracksResponse tracksResponse) {
        tracksResponse.getTracks().forEach(track -> {
            TrackEntity trackEntity = trackRepository.findBySpotifyId(track.getSpotifyId()).orElseThrow(() -> new EntityExistsException("Track not found in database for Spotify ID: " + track.getSpotifyId()));

            if (trackEntity != null) {
                TrackPopularityHistory newHistory = TrackPopularityHistory.builder()
                        .spotifyId(track.getSpotifyId())
                        .popularity(track.getPopularity())
                        .timestamp(LocalDateTime.now())
                        .track(trackEntity)
                        .build();
                trackPopularityHistoryRepository.save(newHistory);
            } else {
                log.warn("Track not found in database for Spotify ID: {}", track.getSpotifyId());
            }
        });
    }

    private HttpRequest buildRequestForTopTracksByYear(String url) {
        String fields = "name,followers,tracks.items%28track%28id%2Cname%2Cartists%28id%2Cname%29%2Calbum%28id%2Cimages%2Cname%2Crelease_date%2Ctotal_tracks%29%2Cpopularity%2Cduration_ms%29%29&limit=100";
        String urlWithFields = url + "?fields=" + fields;
        return HttpRequest.newBuilder()
                .uri(URI.create(urlWithFields))
                .header("Authorization", "Bearer " + apiConfig.getSpotifyAccessToken())
                .GET()
                .build();
    }
    private HttpRequest buildRequestForTrackPopularity(String ids) {
        String url = "https://api.spotify.com/v1/tracks?ids=" + ids;
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + apiConfig.getSpotifyAccessToken())
                .GET()
                .build();
    }
}

package com.soundstock.services.api;

import com.soundstock.config.ApiConfig;
import com.soundstock.mapper.SongMapper;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.dto.api.lastfm.Track;
import com.soundstock.model.dto.api.lastfm.TracksResponse;
import com.soundstock.repository.SongRepository;
import com.soundstock.services.helpers.HttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import static com.soundstock.enums.ApiEndpoints.LAST_FM_GET_MOST_POPULAR_SONGS;

@Service
@Slf4j
@RequiredArgsConstructor
public class LastFmService {
    private final ApiConfig apiConfig;
    private final HttpClientService httpClientService;
    private final TrackMapper trackMapper;
    private final SongMapper songMapper;
    private final SongRepository songRepository;

    public List<Track> getMostPopularSongs() {
        String url = LAST_FM_GET_MOST_POPULAR_SONGS.toString();
        HttpRequest request = buildRequestForLastFm(url);
        log.debug("Request URI: {}", request.uri());
        TracksResponse responseBody = httpClientService.sendRequest(request, TracksResponse.class);
        return responseBody.getTracks().getTrack();
    }

    private HttpRequest buildRequestForLastFm(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url + "api_key=" + apiConfig.getLastfmApikey() + "&format=json"))
                .header("Accept", "application/json")
                .GET()
                .build();
    }
    public List<SongDTO> saveTracksAsEntity(List<Track> tracks) {
        List<SongDTO> songDTOS = trackMapper.trackToSongDTOList(tracks);
        songRepository.saveAll(songMapper.mapSongDTOtoSongEntityList(songDTOS));
        return songDTOS;
    }

}

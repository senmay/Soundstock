package com.soundstock.services.api;

import com.soundstock.config.ApiConfig;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.mapper.TrackLastFmMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.lastfm.TrackLastFm;
import com.soundstock.model.dto.api.lastfm.TracksResponseLastFm;
import com.soundstock.repository.ArtistRepository;
import com.soundstock.repository.TrackRepository;
import com.soundstock.services.ArtistService;
import com.soundstock.services.helpers.HttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import static com.soundstock.enums.ApiEndpointsConstants.LAST_FM_GET_MOST_POPULAR_SONGS;


@Service
@Slf4j
@RequiredArgsConstructor
public class LastFmService {
    private final ArtistRepository artistRepository;
    private final ApiConfig apiConfig;
    private final HttpClientService httpClientService;
    private final TrackLastFmMapper trackLastFmMapper;
    private final TrackMapper trackMapper;
    private final TrackRepository trackRepository;
    private final ArtistService artistService;

    public List<TrackLastFm> getMostPopularSongs() {
        HttpRequest request = buildRequestForLastFm(LAST_FM_GET_MOST_POPULAR_SONGS);
        log.debug("Request URI: {}", request.uri());
        TracksResponseLastFm responseBody = httpClientService.sendRequest(request, TracksResponseLastFm.class);
        return responseBody.getTracks().getTrackLastFm();
    }

    private HttpRequest buildRequestForLastFm(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url + "api_key=" + apiConfig.getLastfmApikey() + "&format=json"))
                .header("Accept", "application/json")
                .GET()
                .build();
    }
    public List<TrackDTO> saveTracksAsEntity(List<TrackLastFm> trackLastFms) {
        List<TrackDTO> trackDTOS = trackLastFmMapper.trackToTrackDTOList(trackLastFms);
        for (TrackDTO trackDTO : trackDTOS) {
            // Sprawdź i dodaj artystów, jeśli nie istnieją
            for(ArtistDTO artistDTO : trackDTO.getArtists()) {
                if (!artistExists(artistDTO.getName())) {
                    artistService.addArtist(artistDTO);
                }
            }
            // Zapisz utwór niezależnie od tego, czy artyści istnieli wcześniej
            trackRepository.save(trackMapper.mapTrackDTOtoTrackEntity(trackDTO));
        }
        return trackDTOS;
    }
    private boolean artistExists(String artistName) {
        return artistRepository.existsByNameIgnoreCase(artistName);
    }
}

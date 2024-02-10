package com.soundstock.services.api;

import com.soundstock.config.ApiConfig;
import com.soundstock.model.dto.api.spotify.ItemSpotify;
import com.soundstock.model.dto.api.spotify.TracksResponseSpotify;
import com.soundstock.services.helpers.HttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;

import static com.soundstock.enums.ApiEndpointsConstants.YEAR_TO_SPOTIFY_URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpotifyService {
    private final HttpClientService httpClientService;
    private final ApiConfig apiConfig;
    public List<ItemSpotify> getMostPopularSongsFromYear(Integer year) {
        String url = YEAR_TO_SPOTIFY_URL.getOrDefault(year, "bad url");
        HttpRequest request = buildRequestForSpotify(url);
        log.debug("Request URI: {}", request.uri());
        TracksResponseSpotify responseBody = httpClientService.sendRequest(request, TracksResponseSpotify.class);
        return responseBody.getTracks().getItems();
    }

    private HttpRequest buildRequestForSpotify(String url) {
        String fields = "tracks.items%28track%28id%2Cname%2Cartists%28id%2Cname%29%2Calbum%28id%2Cimages%2Cname%2Crelease_date%2Ctotal_tracks%29%2Cpopularity%2Cduration_ms%29%29&limit=100";
        String urlWithFields = url + "?fields=" + fields;
        return HttpRequest.newBuilder()
                .uri(URI.create(urlWithFields))
                .header("Authorization", "Bearer " + apiConfig.getSpotifyAccessToken())
                .GET()
                .build();
    }
}

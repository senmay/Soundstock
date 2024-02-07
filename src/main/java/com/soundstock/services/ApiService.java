package com.soundstock.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundstock.config.ApiConfig;
import com.soundstock.mapper.SongMapper;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
import com.soundstock.model.dto.api.lastfm.Track;
import com.soundstock.model.dto.api.lastfm.TracksResponse;
import com.soundstock.repository.SongRepository;
import com.soundstock.services.helpers.HttpClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.soundstock.enums.LastFmEndpoints.LAST_FM_BASEURL;
import static com.soundstock.enums.LastFmEndpoints.MOST_POPULAR_SONGS;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClientService httpClientService;
    private final TrackMapper trackMapper;
    private final SongMapper songMapper;
    private final SongRepository songRepository;
    public List<CoingeckoStockDTO> fetchCoins() {
        String url = apiConfig.getCoingeckoUrl() + "/coins/list/";
        HttpRequest request = buildRequestForCoingecko(url);
        try {
            // Typ odpowiedzi zmieniony na String, ponieważ przetwarzamy JSON poniżej
            String responseBody = httpClientService.sendRequest(request, String.class);
            List<JsonNode> jsonNodeList = objectMapper.readValue(responseBody, new TypeReference<List<JsonNode>>() {});
            return mapCoinsToDTO(jsonNodeList);
        } catch (IOException e) {
            log.warn("Error: Unable to fetch coingecko API response. " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<CoingeckoStockDTO> mapCoinsToDTO(List<JsonNode> list){
        List<CoingeckoStockDTO> coingeckoStockDTOList = new ArrayList<>();
        for(JsonNode node : list){
            CoingeckoStockDTO coingeckoStockDTO = new CoingeckoStockDTO();
            coingeckoStockDTO.setSymbol(node.get("symbol").asText());
            coingeckoStockDTO.setName(node.get("name").asText());
            coingeckoStockDTOList.add(coingeckoStockDTO);
        }
        return coingeckoStockDTOList;
    }
    public List<Track> getMostPopularSongs() {
        String url = LAST_FM_BASEURL.getEndpoint() + MOST_POPULAR_SONGS.getEndpoint();
        HttpRequest request = buildRequestForLastFm(url);
        log.debug("Request URI: {}", request.uri());
        TracksResponse responseBody = httpClientService.sendRequest(request, TracksResponse.class);
        return responseBody.getTracks().getTrack();
    }
    private HttpRequest buildRequestForCoingecko(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(apiConfig.getCoingeckoUrl() + url + "?x_cg_demo_api_key=" + apiConfig.getCoingeckoApikey()))
                .header("Accept", "application/json")
                .GET()
                .build();
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


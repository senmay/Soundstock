package com.soundstock.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundstock.config.ApiConfig;
import com.soundstock.model.dto.CoingeckoStockDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final ApiConfig apiConfig;
    String getCoinLists = "/coins/list/";
    String getSongs = "/?method=chart.gettoptracks&";
    public List<CoingeckoStockDTO> fetchCoins() {
        try {
            HttpRequest request = buildRequestForCoingecko(getCoinLists);
            HttpResponse<String> response = sendRequest(request);
            List<JsonNode> jsonNodeList = handleResponse(response);
            return processCoins(jsonNodeList);
        } catch (IOException | InterruptedException e) {
            log.warn("Error: Unable to fetch coingecko API response. " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public List<CoingeckoStockDTO> processCoins(List<JsonNode> list){
        List<CoingeckoStockDTO> coingeckoStockDTOList = new ArrayList<>();
        for(JsonNode node : list){
            CoingeckoStockDTO coingeckoStockDTO = new CoingeckoStockDTO();
            coingeckoStockDTO.setSymbol(node.get("symbol").asText());
            coingeckoStockDTO.setName(node.get("name").asText());
            coingeckoStockDTOList.add(coingeckoStockDTO);
        }
        return coingeckoStockDTOList;
    }
    public List<JsonNode> getMostPopularSongs() {
        try {
            HttpRequest request = buildRequestForLastFm(getSongs);
            HttpResponse<String> response = sendRequest(request);
            System.out.println(response.body());
            JsonNode rootNode = new ObjectMapper().readTree(response.body());
            JsonNode tracksNode = rootNode.path("toptracks").path("track");
            if (tracksNode.isArray()) {
                List<JsonNode> tracks = new ArrayList<>();
                for (JsonNode node : tracksNode) {
                    tracks.add(node);
                }
                return tracks;
            }
        } catch (IOException | InterruptedException e) {
            log.warn("Error: Unable to fetch lastfm API response. " + e.getMessage());
        }
        return Collections.emptyList();
    }
    private HttpRequest buildRequestForLastFm(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(apiConfig.getLastFmUrl() + url + "api_key=" + apiConfig.getLastFmKey() + "&format=json"))
                .header("Accept", "application/json")
                .GET()
                .build();
    }
    private HttpRequest buildRequestForCoingecko(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(apiConfig.getGeckoUrl() + url + "?x_cg_demo_api_key=" + apiConfig.getGeckoKey()))
                .header("Accept", "application/json")
                .GET()
                .build();
    }
    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    private List<JsonNode> handleResponse(HttpResponse<String> response) throws JsonProcessingException {
        if(response.statusCode() == 200) {
            String responseBody = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode[] jsonNode = objectMapper.readValue(responseBody, JsonNode[].class);
            List<JsonNode> ParserDTOList = Arrays.asList(jsonNode);
            return ParserDTOList;
        } else {
            log.warn("Error: Unable to fetch from API response. Status code: " + response.statusCode());
            return Collections.emptyList();
        }
    }

}


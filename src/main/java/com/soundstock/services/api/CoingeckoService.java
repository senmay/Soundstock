package com.soundstock.services.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundstock.config.ApiConfig;
import com.soundstock.enums.ApiEndpoints;
import com.soundstock.model.dto.api.coingecko.CoingeckoStockDTO;
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

import static com.soundstock.enums.ApiEndpoints.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoingeckoService {
    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;
    private final HttpClientService httpClientService;
    public List<CoingeckoStockDTO> fetchCoins() {
        HttpRequest request = buildRequestForCoingecko(COINGECKO_GET_COINLIST);
        log.debug(request.uri().toString());
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
    private HttpRequest buildRequestForCoingecko(ApiEndpoints endpoint) {
        return HttpRequest.newBuilder()
                .uri(URI.create(endpoint + "?x_cg_demo_api_key=" + apiConfig.getCoingeckoApikey()))
                .header("Accept", "application/json")
                .GET()
                .build();
    }
}

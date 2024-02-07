package com.soundstock.services.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpClientService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;
    private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public <T> T sendRequest(HttpRequest request, Class<T> responseType) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), responseType);
            } else {
                log.warn("Error: Unable to fetch from API response. Status code: " + response.statusCode());
                throw new IOException("Error fetching from API: Status code " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.error("Exception occurred while sending HTTP request: ", e);
            throw new RuntimeException(e);
        }
    }
}

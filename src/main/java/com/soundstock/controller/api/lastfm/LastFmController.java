package com.soundstock.controller.api.lastfm;

import com.fasterxml.jackson.databind.JsonNode;
import com.soundstock.model.dto.api.lastfm.Track;
import com.soundstock.services.ApiService;
import com.soundstock.services.helpers.ApiCoordinatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("lastfm/v1")
@Slf4j
public class LastFmController {
    private final ApiService apiService;
    private final ApiCoordinatorService apiCoordinatorService;
    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getMostPopularSongs(){
        log.info("Pobieranie listy piosenek");
        return apiService.getMostPopularSongs();
    }
    @GetMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void importMostPopularSongs(){
        log.info("Importowanie listy piosenek");
        apiCoordinatorService.fetchAndSaveTrackDataToDatabase();
    }
}

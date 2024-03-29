package com.soundstock.controller.api.lastfm;

import com.soundstock.model.dto.api.lastfm.TrackLastFm;
import com.soundstock.services.api.LastFmService;
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
    private final LastFmService lastFmService;
    private final ApiCoordinatorService apiCoordinatorService;
    @GetMapping("/songs")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackLastFm> getMostPopularSongs(){
        log.info("Pobieranie listy piosenek");
        return lastFmService.getMostPopularSongs();
    }
    @GetMapping("/import")
    @ResponseStatus(HttpStatus.OK)
    public void importMostPopularSongs(){
        log.info("Importowanie listy piosenek");
        apiCoordinatorService.fetchAndSaveTrackDataToDatabase();
    }
}

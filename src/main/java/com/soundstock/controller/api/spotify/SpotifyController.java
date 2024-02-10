package com.soundstock.controller.api.spotify;

import com.soundstock.model.dto.api.spotify.ItemSpotify;
import com.soundstock.model.dto.api.spotify.TrackSpotify;
import com.soundstock.services.api.SpotifyService;
import com.soundstock.services.helpers.ApiCoordinatorService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("spotify/v1")

public class SpotifyController {
    private final SpotifyService spotifyService;
    private final ApiCoordinatorService apiCoordinatorService;
    @GetMapping("/yearly-top-tracks")
    public List<ItemSpotify> getYearlyTopTracks(@Param("year") Integer year) {
        return spotifyService.getMostPopularSongsFromYear(year);
    }
    @GetMapping("/fetch-and-save")
    public void fetchAndSaveSpotifyTrackToDatabase(@Param("year") Integer year) {
        apiCoordinatorService.fetchAndSaveSpotifyTrackToDatabase(year);
    }
}

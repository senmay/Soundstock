package com.soundstock.controller;

import com.soundstock.model.dto.SimpleTrackDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.services.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("track/v1")
public class TrackController {
    private final TrackService trackService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addTrack(@RequestBody TrackDTO trackDTO) {
        return trackService.addTrack(trackDTO);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleTrackDTO> getAllTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping("/getSong")
    @ResponseStatus(HttpStatus.OK)
    public TrackDTO getTrackByTitleAndArtist(@PathVariable String title) {
        return trackService.getTrackByTitle(title);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTrackById(@PathVariable Long id) {
        trackService.deleteTrack(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackDTO getTrackByTitleAndArtist(@PathVariable Long id) {
        return trackService.getTrackById(id);
    }


}


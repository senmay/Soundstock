package com.soundstock.controller;

import com.soundstock.model.dto.SongDTO;
import com.soundstock.services.SongService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("song/v1")
public class SongController {
    private final SongService songService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addSong(@RequestBody SongDTO songDTO) {
        return songService.addSong(songDTO);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<SongDTO> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/getSong")
    @ResponseStatus(HttpStatus.OK)
    public SongDTO getSongByTitleAndArtist(@PathVariable String title) {
        return songService.getSongByTitle(title);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteSongById(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SongDTO getSongByTitleAndArtist(@PathVariable Long id) {
        return songService.getSongById(id);
    }

}


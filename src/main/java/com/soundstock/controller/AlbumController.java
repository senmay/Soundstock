package com.soundstock.controller;

import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.services.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("album/v1")

public class AlbumController {
    private final AlbumService albumService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String createAlbum(@RequestBody AlbumDTO albumDTO) {
        return albumService.createAlbum(albumDTO);
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumDTO> getAlbums() {
        return albumService.getAlbums();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumDTO getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id);
    }

}

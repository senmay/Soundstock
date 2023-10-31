package com.soundstock.controller;

import com.soundstock.mapper.AlbumMapper;
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
    private final AlbumMapper albumMapper;
    private final AlbumService albumService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createAlbum(@RequestBody AlbumDTO albumDTO) {
        return albumService.createAlbum(albumDTO);
    }
    @GetMapping("/all")
    public List<AlbumDTO> getAlbums() {
        return albumService.getAlbums();
    }
    @GetMapping("/{id}")
    public AlbumDTO getAlbumById(@PathVariable Long id) {

        return albumService.getAlbumById(id);
    }
    @PutMapping("/{id}")
    public String updateAlbum(@PathVariable Long id, @RequestBody AlbumDTO albumDTO) {
        //TODO
        return null;
    }



}

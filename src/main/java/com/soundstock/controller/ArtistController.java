package com.soundstock.controller;

import com.soundstock.mapper.ArtistMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.services.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("aritst/v1")
public class ArtistController {
    private final ArtistService artistService;
    private final ArtistMapper artistMapper;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addArtist(@RequestBody ArtistDTO artistDTO){
        artistService.addArtist(artistDTO);
        return "Artist added";
    }

}

package com.soundstock.controller;

import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.services.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("artist/v1")
public class ArtistController {
    private final ArtistService artistService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addArtist(@RequestBody ArtistDTO artistDTO) {
        artistService.addArtist(artistDTO);
        return "Artist added";
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistDTO> getArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistDTO getArtist(@PathVariable Long id) {
        return artistService.getArtistById(id);
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistDTO getArtistByName(@PathVariable String name) {
        return artistService.getArtistByName(name);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id) {
        artistService.deleteArtistById(id);
    }
}

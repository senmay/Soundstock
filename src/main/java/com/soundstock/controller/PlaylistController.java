package com.soundstock.controller;

import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.services.PlaylistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/playlist/v1")
@Slf4j
@AllArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    @GetMapping("/all")
    public List<PlaylistDTO> getAllPlaylists(){
        return playlistService.getAllPlaylists();
    }
}

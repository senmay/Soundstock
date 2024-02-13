package com.soundstock.services;

import com.soundstock.mapper.PlaylistMapper;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.mapper.TrackSpotifyMapper;
import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.model.dto.api.spotify.ItemSpotify;
import com.soundstock.model.entity.PlaylistEntity;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.repository.PlaylistRepository;
import com.soundstock.services.api.SpotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaylistService {
    private final PlaylistMapper playlistMapper;

    private final PlaylistRepository playlistRepository;
    public boolean checkIfExists(String name){
        return playlistRepository.existsByName(name);
    }
    public Optional<PlaylistEntity> findByName(String name){
        return playlistRepository.findByName(name);
    }
    public PlaylistEntity savePlaylist(PlaylistDTO playlist){
        return playlistRepository.save(playlistMapper.toEntity(playlist));
    }
    public PlaylistEntity savePlaylist(PlaylistEntity playlist){
        return playlistRepository.save(playlist);
    }
    public List<PlaylistDTO> getAllPlaylists(){
        return playlistMapper.toDTOList(playlistRepository.findAll());
    }
    public PlaylistEntity ensurePlaylistExists(String name) {
        return findByName(name)
                .orElseGet(() -> savePlaylist(PlaylistEntity.builder().name(name).build()));
    }

}

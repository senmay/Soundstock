package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.PlaylistEntity;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.repository.TrackRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.soundstock.exceptions.ErrorMessages.SONG_EXISTS;
import static com.soundstock.exceptions.ErrorMessages.SONG_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackService {
    //TODO create integration tests
    private final TrackRepository trackRepository;
    private final TrackMapper trackMapper;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final PlaylistService playlistService;

    public List<TrackDTO> getAllTracks() {
        List<TrackEntity> trackEntityList = trackRepository.findAll();
        return trackMapper.mapTrackEntityToDTOList(trackEntityList);
    }

    public TrackDTO getTrackByTitle(String title) {
        Optional<TrackEntity> songEntity = trackRepository.findByTitle(title);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound(SONG_NOT_FOUND, getClass());
        }
        return trackMapper.mapTrackEntityToTrackDTO(songEntity.get());
    }
    public TrackDTO getTrackById(Long id) {
        Optional<TrackEntity> songEntity = trackRepository.findById(id);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound(SONG_NOT_FOUND, getClass());
        }
        return trackMapper.mapTrackEntityToTrackDTO(songEntity.get());
    }
    @Transactional
    public String addTrack(TrackDTO trackDTO) {
        checkIfTrackExists(trackDTO);
        List<ArtistEntity> artists = trackDTO.getArtists().stream()
                .map(artistService::addArtist)
                .toList();
        AlbumEntity album = albumService.addAlbum(trackDTO.getAlbum());
        TrackEntity trackEntity = trackMapper.mapTrackDTOtoTrackEntity(trackDTO);
        trackEntity.setArtists(artists);
        trackEntity.setAlbum(album);
        trackRepository.save(trackEntity);
        return "Track added";
    }
    @Transactional
    public String addTrack(TrackDTO trackDTO, PlaylistDTO playlistDTO) {
        checkIfTrackExists(trackDTO);
        List<ArtistEntity> artists = trackDTO.getArtists().stream()
                .map(artistService::addArtist)
                .toList();
        AlbumEntity album = albumService.addAlbum(trackDTO.getAlbum());
        TrackEntity trackEntity = trackMapper.mapTrackDTOtoTrackEntity(trackDTO);
        PlaylistEntity playlist = ensurePlaylistExists(playlistDTO);
        trackEntity.setPlaylists(List.of(playlist));
        trackEntity.setArtists(artists);
        trackEntity.setAlbum(album);
        trackRepository.save(trackEntity);
        return "Track added";
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
    private void checkIfTrackExists(TrackDTO trackDTO) {
        Optional<TrackEntity> existingTrack = trackRepository.findByTitle(trackDTO.getTitle());
        if (existingTrack.isPresent()) {
            throw new EntityExistsException(SONG_EXISTS);
        }
    }
    private PlaylistEntity ensurePlaylistExists(PlaylistDTO playlistDTO) {
        return playlistService.findByName(playlistDTO.getName())
                .orElseGet(() -> playlistService.savePlaylist(playlistDTO));
    }
}

package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.model.dto.SimpleTrackDTO;
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

    public List<SimpleTrackDTO> getAllTracks() {
        List<TrackEntity> trackEntityList = trackRepository.findAll();
        return trackMapper.toSimpleDTO(trackEntityList);
    }

    public TrackDTO getTrackByTitle(String title) {
        Optional<TrackEntity> songEntity = trackRepository.findByTitle(title);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound(SONG_NOT_FOUND, getClass());
        }
        return trackMapper.toDTO(songEntity.get());
    }
    public TrackDTO getTrackById(Long id) {
        Optional<TrackEntity> songEntity = trackRepository.findById(id);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound(SONG_NOT_FOUND, getClass());
        }
        return trackMapper.toDTO(songEntity.get());
    }
    @Transactional
    public String addTrack(TrackDTO trackDTO) {
        checkIfTrackExists(trackDTO);
        TrackEntity trackEntity = trackMapper.toEntity(trackDTO);
        addDetailsToTrack(trackDTO, trackEntity, Optional.empty()); // Brak playlisty
        trackRepository.save(trackEntity);
        return "Track added";
    }
    @Transactional
    public String addTrack(TrackDTO trackDTO, PlaylistEntity playlistEntity) {
        checkIfTrackExists(trackDTO);
        System.out.println(trackDTO.getArtists());
        TrackEntity trackEntity = trackMapper.toEntity(trackDTO);
        System.out.println(trackEntity.getArtists());
        PlaylistEntity playlist = playlistService.ensurePlaylistExists(playlistEntity.getName());
        addDetailsToTrack(trackDTO, trackEntity, Optional.of(playlist));
        trackRepository.save(trackEntity);
        return "Track added";
    }
    public TrackEntity getTrackBySpotifyId(String spotifyId) {
        return trackRepository.findBySpotifyId(spotifyId)
                .orElseThrow(() -> new ObjectNotFound(SONG_NOT_FOUND, getClass()));
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
    private void checkIfTrackExists(TrackDTO trackDTO) {
        Optional<TrackEntity> existingTrack = trackRepository.findByTitle(trackDTO.getTitle());
        if (existingTrack.isPresent()) {
            log.info(SONG_EXISTS);
        }
    }
    private void addDetailsToTrack(TrackDTO trackDTO, TrackEntity trackEntity, Optional<PlaylistEntity> playlistEntity) {
        List<ArtistEntity> artists = artistService.ensureArtistsExists(trackDTO);
        AlbumEntity album = albumService.addAlbum(trackDTO.getAlbum());
        trackEntity.addArtists(artists);
        trackEntity.addAlbum(album);
        playlistEntity.ifPresent(trackEntity::addPlaylist);
    }
}

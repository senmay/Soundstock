package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.repository.TrackRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<TrackDTO> getAllSongs() {
        List<TrackEntity> trackEntityList = trackRepository.findAll();
        return trackMapper.mapTrackEntityToDTOList(trackEntityList);
    }

    public TrackDTO getSongByTitle(String title) {
        Optional<TrackEntity> songEntity = trackRepository.findByTitle(title);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound(SONG_NOT_FOUND, getClass());
        }
        return trackMapper.mapTrackEntityToSongDTO(songEntity.get());
    }
    public TrackDTO getSongById(Long id) {
        Optional<TrackEntity> songEntity = trackRepository.findById(id);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound(SONG_NOT_FOUND, getClass());
        }
        return trackMapper.mapTrackEntityToSongDTO(songEntity.get());
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

    public void deleteSong(Long id) {
        trackRepository.deleteById(id);
    }
    private void checkIfTrackExists(TrackDTO trackDTO) {
        Optional<TrackEntity> existingTrack = trackRepository.findByTitle(trackDTO.getTitle());
        if (existingTrack.isPresent()) {
            throw new EntityExistsException(SONG_EXISTS);
        }
    }

    private void ensureArtistExists(List<ArtistDTO> artistDTO) {
        for (ArtistDTO artist : artistDTO) {
            artistService.getArtistByNameOptional(artist.getName())
                    .orElseGet(() -> artistService.addArtist(artist));
        }
    }
}

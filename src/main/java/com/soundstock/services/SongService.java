package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.SongMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.SongEntity;
import com.soundstock.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
    //TODO create integration tests
    private final SongRepository songRepository;
    private final SongMapper songMapper;
    private final ArtistService artistService;

    public List<SongDTO> getAllSongs() {
        List<SongEntity> songEntityList = songRepository.findAll();
        return songMapper.mapSongEntityToDTOList(songEntityList);
    }

    public SongDTO getSongByTitle(String title) {
        Optional<SongEntity> songEntity = songRepository.findByTitle(title);
        if (songEntity.isEmpty()) {
            throw new ObjectNotFound("Song doesn't exist in database", getClass());
        }
        return songMapper.mapSongEntityToSongDTO(songEntity.get());
    }

    public String addSong(SongDTO songDTO) {
        checkIfSongExists(songDTO);
        ensureArtistExists(songDTO.getArtist());
        ArtistEntity artistEntity = ensureArtistExists(songDTO.getArtist());
        SongEntity songEntity = songMapper.mapSongDTOtoSongEntity(songDTO, artistEntity);
        songRepository.save(songEntity);
        return "Song added";
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public void deleteAllSongs() {
        songRepository.deleteAll();
    }

    private void checkIfSongExists(SongDTO songDTO) {
        Optional<SongEntity> existingSong = songRepository.findByTitleAndArtist_Name(songDTO.getTitle(), songDTO.getArtist().getName());
        if (existingSong.isPresent()) {
            throw new ObjectNotFound("Song already in database", getClass());
        }
    }

    private ArtistEntity ensureArtistExists(ArtistDTO artistDTO) {
        return artistService.getArtistByNameOptional(artistDTO.getName())
                .orElseGet(() -> artistService.addArtist(artistDTO));
    }
}

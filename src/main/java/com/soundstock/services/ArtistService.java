package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.ArtistMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public List<ArtistDTO> getAllArtists() {
        List<ArtistEntity> artistEntityList = artistRepository.findAll();
        return artistMapper.toDTOList(artistEntityList);
    }

    public ArtistDTO getArtistById(Long id) {
        Optional<ArtistEntity> artistEntity = artistRepository.findById(id);
        if (artistEntity.isEmpty()) {
            throw new ObjectNotFound("Artist id doesn't exist in database");
        }
        return artistMapper.toDTO(artistEntity.get());
    }
    public ArtistDTO getArtistByName(String name){
        Optional<ArtistEntity> artistEntity = artistRepository.findByName(name);
        if (artistEntity.isEmpty()){
            throw new ObjectNotFound("Artist doesn't exist in database");
        }
        return artistMapper.toDTO(artistEntity.get());
    }
    public Optional<ArtistEntity> getArtistByNameOptional(String name){
        Optional<ArtistEntity> artistEntity = artistRepository.findByName(name);
        if (artistEntity.isEmpty()){
            log.info("Artist doesn't exist");
        }
        return artistEntity;
    }
    @Transactional
    public ArtistEntity addArtist(ArtistDTO artistDTO) {
        Optional<ArtistEntity> existingArtist = artistRepository.findByName(artistDTO.getName());
        if (existingArtist.isPresent()) {
            return existingArtist.get();
        }
        ArtistEntity artistEntity = artistMapper.toEntity(artistDTO);
        return artistRepository.save(artistEntity);
    }
    @Transactional
    public ArtistEntity addArtist(ArtistEntity artistEntity) {
        Optional<ArtistEntity> existingArtist = artistRepository.findByName(artistEntity.getName());
        if (existingArtist.isPresent()) {
            log.info("Artist already exists in database");
            return existingArtist.get();
        }
        return artistRepository.save(artistEntity);
    }
    @Transactional
    public List<ArtistEntity> addArtists(List<ArtistDTO> artistDTOList) {
        return artistDTOList.stream()
                .map(this::addArtist)
                .toList();
    }

    public void deleteArtistById(Long id){
        artistRepository.deleteById(id);
    }
    public List<ArtistEntity> ensureArtistsExists(TrackDTO trackDTO) {
        return trackDTO.getArtists().stream()
                .map(this::addArtist)
                .toList();
    }

}



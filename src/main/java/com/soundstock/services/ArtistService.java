package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.ArtistMapper;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.repository.AristRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistService {
    private final AristRepository artistRepository;
    private final ArtistMapper artistMapper;

    public List<ArtistDTO> getAllArtists() {
        List<ArtistEntity> artistEntityList = artistRepository.findAll();
        return artistMapper.mapArtistEntitesToDTOList(artistEntityList);
    }

    public ArtistDTO getArtistById(Long id) {
        Optional<ArtistEntity> artistEntity = artistRepository.findById(id);
        if (artistEntity.isEmpty()) {
            throw new ObjectNotFound("Artist id doesn't exist in database");
        }
        return artistMapper.mapArtistEntityToDTO(artistEntity.get());
    }
    public ArtistDTO getArtistByName(String name){
        Optional<ArtistEntity> artistEntity = artistRepository.findByName(name);
        if (artistEntity.isEmpty()){
            throw new ObjectNotFound("Artist doesn't exist in database");
        }
        return artistMapper.mapArtistEntityToDTO(artistEntity.get());
    }
    public Optional<ArtistEntity> getArtistByNameOptional(String name){
        Optional<ArtistEntity> artistEntity = artistRepository.findByName(name);
        if (artistEntity.isEmpty()){
            log.info("Artist doesn't exist");
        }
        return artistEntity;
    }
    public ArtistEntity addArtist(ArtistDTO artistDTO) {
        checkIfArtistExists(artistDTO);
        ArtistEntity artistEntity = artistMapper.mapDTOToArtistEntity(artistDTO);
        return artistRepository.save(artistEntity);
    }
    public void checkIfArtistExists(ArtistDTO artistDTO){
        Optional<ArtistEntity> existingArtist = artistRepository.findByName(artistDTO.getName());
        if (existingArtist.isPresent()) {
            throw new ObjectNotFound("Artist already in database", getClass());
        }
    }
}



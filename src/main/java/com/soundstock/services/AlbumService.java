package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.AlbumMapper;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    @Transactional
    public AlbumEntity addAlbum(AlbumDTO albumDTO) {
        Optional<AlbumEntity> existingAlbum = albumRepository.findByTitle(albumDTO.getTitle());
        if (existingAlbum.isPresent()) {
            return existingAlbum.get();
        }
        AlbumEntity albumEntity = albumMapper.mapDTOToAlbumEntity(albumDTO);
        log.info("Album added to database");
        return albumRepository.save(albumEntity);
    }

    public List<AlbumDTO> getAlbums() {
        List<AlbumEntity> albumsEntities = albumRepository.findAll();
        return albumMapper.mapAlbumEntitesToDTOList(albumsEntities);
    }

    public AlbumDTO getAlbumById(Long id) {
        Optional<AlbumEntity> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return albumMapper.mapAlbumEntityToDTO(album.get());
        } else {
            throw new ObjectNotFound("Album not found with id: " + id, getClass());
        }
    }
}

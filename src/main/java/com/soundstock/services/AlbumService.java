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

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    @Transactional
    public AlbumEntity addAlbum(AlbumDTO album) {
        Optional<AlbumEntity> existingAlbum = albumRepository.findByTitle(album.getTitle());
        if (existingAlbum.isPresent()) {
            return existingAlbum.get();
        }
        log.info("Album added to database");
        return albumRepository.save(albumMapper.toEntity(album));
    }
    @Transactional
    public AlbumEntity addAlbum(AlbumEntity albumEntity) {
        Optional<AlbumEntity> existingAlbum = albumRepository.findByTitle(albumEntity.getTitle());
        if (existingAlbum.isPresent()) {
            return existingAlbum.get();
        }
        log.info("Album added to database");
        return albumRepository.save(albumEntity);
    }
    public List<AlbumDTO> getAlbums() {
        List<AlbumEntity> albumsEntities = albumRepository.findAll();
        return albumMapper.toDTO(albumsEntities);
    }

    public AlbumDTO getAlbumById(Long id) {
        Optional<AlbumEntity> album = albumRepository.findById(id);
        if (album.isPresent()) {
            return albumMapper.toDTO(album.get());
        } else {
            throw new ObjectNotFound("Album not found with id: " + id, getClass());
        }
    }
    private boolean checkIfAlbumExists(String name) {
        Optional<AlbumEntity> albumEntity = albumRepository.findByTitle(name);
        if (albumEntity.isPresent()) {
            log.info("Album already exists in database");
            return true;
        }
        return false;
    }
}

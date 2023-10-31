package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.AlbumMapper;
import com.soundstock.model.Album;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public String createAlbum(AlbumDTO albumDTO){
        AlbumEntity albumEntity = albumMapper.mapDTOToAlbumEntity(albumDTO);
        albumRepository.save(albumEntity);
        return "Album created";
    }

    public List<AlbumDTO> getAlbums(){
        List<AlbumEntity> albumsEntities = albumRepository.findAll();
        return albumMapper.mapAlbumEntitesToDTOList(albumsEntities);
    }
    public AlbumDTO getAlbumById(Long id){
        Optional<AlbumEntity> album = albumRepository.findById(id);
        if (album.isPresent()){
            return albumMapper.mapAlbumEntityToDTO(album.get());
        } else {
            throw new ObjectNotFound("Album not found with id: " + id, getClass());
        }
    }

}

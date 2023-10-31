package com.soundstock.mapper;

import com.soundstock.model.Album;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.entity.AlbumEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AlbumMapper {
    AlbumDTO mapToAlbumDTO(Album album);
    Album mapToAlbum(AlbumDTO albumDTO);
    Album mapToAlbum(AlbumEntity albumEntity);
    List<AlbumDTO> mapToAlbumDTOList(List<Album> albums);
    List<Album> mapToAlbumList(List<AlbumEntity> albums);
    AlbumEntity mapToAlbumEntity(Album album);
    List<AlbumDTO> mapAlbumEntitesToDTOList(List<AlbumEntity> albumEnt);
    AlbumDTO mapAlbumEntityToDTO(AlbumEntity albumEntity);
    AlbumEntity mapDTOToAlbumEntity(AlbumDTO albumDTO);


}

package com.soundstock.mapper;

import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.entity.AlbumEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AlbumMapper {
    List<AlbumDTO> mapAlbumEntitesToDTOList(List<AlbumEntity> albumEnt);
    AlbumDTO mapAlbumEntityToDTO(AlbumEntity albumEntity);
    AlbumEntity mapDTOToAlbumEntity(AlbumDTO albumDTO);


}

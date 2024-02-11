package com.soundstock.mapper;

import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.util.CycleAvoidingMappingContext;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface AlbumMapper {
    List<AlbumDTO> mapAlbumEntitesToDTOList(List<AlbumEntity> albumEnt);
    AlbumDTO mapAlbumEntityToDTO(AlbumEntity albumEntity, CycleAvoidingMappingContext context);
    AlbumEntity mapDTOToAlbumEntity(AlbumDTO albumDTO);
    default AlbumDTO mapAlbumEntityToDTO(AlbumEntity albumEntity) {
        return mapAlbumEntityToDTO(albumEntity, new CycleAvoidingMappingContext());
    }


}

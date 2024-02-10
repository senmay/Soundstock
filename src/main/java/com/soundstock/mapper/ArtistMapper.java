package com.soundstock.mapper;

import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ArtistMapper {
    List<ArtistDTO> mapArtistEntitesToDTOList(List<ArtistEntity> artistEntities);
    ArtistDTO mapArtistEntityToDTO(ArtistEntity albumEntity);
    @Mapping(target = "id", ignore = true)
    ArtistEntity mapDTOToArtistEntity(ArtistDTO albumDTO);
}

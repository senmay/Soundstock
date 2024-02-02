package com.soundstock.mapper;

import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ArtistMapper {
    List<ArtistDTO> mapArtistEntitesToDTOList(List<ArtistEntity> artistEntities);
    ArtistDTO mapArtistEntityToDTO(ArtistEntity albumEntity);
    ArtistEntity mapDTOToArtistEntity(ArtistDTO albumDTO);
}

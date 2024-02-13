package com.soundstock.mapper;

import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.api.spotify.ArtistSpotify;
import com.soundstock.model.entity.ArtistEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ArtistMapper {
    List<ArtistDTO> toDTOList(List<ArtistEntity> artistEntities);
    ArtistDTO toDTO(ArtistEntity albumEntity);
    @Mapping(target = "id", ignore = true)
    ArtistEntity toEntity(ArtistDTO albumDTO);
    @Mapping(target = "spotifyId", source = "spotifyId")
    ArtistEntity toEntity(ArtistSpotify artistSpotify);

}

package com.soundstock.mapper;

import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.api.spotify.AlbumSpotify;
import com.soundstock.model.entity.AlbumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface AlbumMapper {
    List<AlbumDTO> toDTO(List<AlbumEntity> albumEnt);
    AlbumDTO toDTO(AlbumEntity albumEntity);
    AlbumEntity toEntity(AlbumDTO albumDTO);
    @Mapping(target = "tracks", ignore = true)
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "spotifyId", source = "spotifyId")
    @Mapping(target = "title", source = "title")
    AlbumEntity toEntity(AlbumSpotify albumSpotify);

}

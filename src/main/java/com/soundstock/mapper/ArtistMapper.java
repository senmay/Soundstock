package com.soundstock.mapper;

import com.soundstock.model.Artist;
import com.soundstock.model.Artist;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ArtistMapper {
    ArtistDTO mapToArtistDTO(Artist album);
    Artist mapToArtist(ArtistDTO albumDTO);
    Artist mapToArtist(ArtistEntity albumEntity);
    List<ArtistDTO> mapToArtistDTOList(List<Artist> albums);
    List<Artist> mapToArtistList(List<ArtistEntity> albums);
    ArtistEntity mapToArtistEntity(Artist album);
    List<ArtistDTO> mapArtistEntitesToDTOList(List<ArtistEntity> artistEntities);
    ArtistDTO mapArtistEntityToDTO(ArtistEntity albumEntity);
    ArtistEntity mapDTOToArtistEntity(ArtistDTO albumDTO);
}

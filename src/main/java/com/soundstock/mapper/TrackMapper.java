package com.soundstock.mapper;

import com.github.javafaker.Artist;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.lastfm.ArtistLastFm;
import com.soundstock.model.entity.TrackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper
public interface TrackMapper {
    List<TrackDTO> mapTrackEntityToDTOList(List<TrackEntity> songEntities);
    TrackDTO mapTrackEntityToSongDTO(TrackEntity trackEntity);
    @Mapping(target="artists", source = "trackDTO.artists")
    @Mapping(target="id", ignore = true)
    TrackEntity mapTrackDTOtoTrackEntity(TrackDTO trackDTO);
    List<TrackEntity> mapSongDTOtoSongEntityList(List<TrackDTO> trackDTOS);
}

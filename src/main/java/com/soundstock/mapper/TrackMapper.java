package com.soundstock.mapper;

import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.util.CycleAvoidingMappingContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TrackMapper {
    List<TrackDTO> mapTrackEntityToDTOList(List<TrackEntity> songEntities);
    TrackDTO mapTrackEntityToSongDTO(TrackEntity trackEntity, CycleAvoidingMappingContext context);
    @Mapping(target="artists", source = "trackDTO.artists")
    @Mapping(target="id", ignore = true)
    TrackEntity mapTrackDTOtoTrackEntity(TrackDTO trackDTO);
    List<TrackEntity> mapSongDTOtoSongEntityList(List<TrackDTO> trackDTOS);
    default TrackDTO mapTrackEntityToSongDTO(TrackEntity trackEntity) {
        return mapTrackEntityToSongDTO(trackEntity, new CycleAvoidingMappingContext());
    }
}

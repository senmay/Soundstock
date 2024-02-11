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
    @Mapping(target = "artists", ignore = true)
    @Mapping(target = "playlists", ignore = true)
    @Mapping(target = "album", ignore = true)
    TrackDTO mapTrackEntityToTrackDTO(TrackEntity trackEntity, CycleAvoidingMappingContext context);
    TrackEntity mapTrackDTOtoTrackEntity(TrackDTO trackDTO);
    List<TrackEntity> mapSongDTOtoSongEntityList(List<TrackDTO> trackDTOS);
    default TrackDTO mapTrackEntityToTrackDTO(TrackEntity trackEntity) {
        return mapTrackEntityToTrackDTO(trackEntity, new CycleAvoidingMappingContext());
    }
}

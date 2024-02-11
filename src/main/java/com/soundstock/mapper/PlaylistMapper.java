package com.soundstock.mapper;

import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.model.dto.SimpleTrackDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.PlaylistEntity;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.util.CycleAvoidingMappingContext;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PlaylistMapper {
    PlaylistEntity mapPlaylistDTOtoPlaylistEntity(PlaylistDTO playlistDTO);
    @Mapping(target = "tracks", expression = "java(mapSimpleTrackDTOList(playlistEntity.getTracks()))")
    PlaylistDTO mapPlaylistEntityToPlaylistDTO(PlaylistEntity playlistEntity, CycleAvoidingMappingContext context);
    List<PlaylistDTO> mapPlaylistEntityListToPlaylistDTOList(List<PlaylistEntity> playlistEntityList);
    List<PlaylistEntity> mapPlaylistDTOListToPlaylistEntityList(List<PlaylistDTO> playlistDTOList);
    default PlaylistDTO mapPlaylistEntityToPlaylistDTO(PlaylistEntity playlistEntity) {
        return mapPlaylistEntityToPlaylistDTO(playlistEntity, new CycleAvoidingMappingContext());
    }
    default List<SimpleTrackDTO> mapSimpleTrackDTOList(List<TrackEntity> trackEntities) {
        List<SimpleTrackDTO> simpleTrackDTOList = new ArrayList<>();
        for (TrackEntity trackEntity : trackEntities) {
            simpleTrackDTOList.add(SimpleTrackDTO.builder()
                    .id(trackEntity.getId())
                    .title(trackEntity.getTitle())
                    .artistName(trackEntity.getArtists().stream().map(ArtistEntity::getName).toList())
                    .build());
        }
        return simpleTrackDTOList;
    }
}

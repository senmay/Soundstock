package com.soundstock.mapper;

import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.model.entity.PlaylistEntity;
import com.soundstock.util.CycleAvoidingMappingContext;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PlaylistMapper {
    PlaylistEntity mapPlaylistDTOtoPlaylistEntity(PlaylistDTO playlistDTO);
    PlaylistDTO mapPlaylistEntityToPlaylistDTO(PlaylistEntity playlistEntity, CycleAvoidingMappingContext context);
    List<PlaylistDTO> mapPlaylistEntityListToPlaylistDTOList(List<PlaylistEntity> playlistEntityList);
    List<PlaylistEntity> mapPlaylistDTOListToPlaylistEntityList(List<PlaylistDTO> playlistDTOList);
    default PlaylistDTO mapPlaylistEntityToPlaylistDTO(PlaylistEntity playlistEntity) {
        return mapPlaylistEntityToPlaylistDTO(playlistEntity, new CycleAvoidingMappingContext());
    }
}

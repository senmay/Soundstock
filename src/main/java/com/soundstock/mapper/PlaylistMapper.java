package com.soundstock.mapper;

import com.soundstock.model.dto.PlaylistDTO;
import com.soundstock.model.dto.SimpleTrackDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.PlaylistEntity;
import com.soundstock.model.entity.TrackEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {TrackMapper.class})
public interface PlaylistMapper {
    PlaylistEntity toEntity(PlaylistDTO playlistDTO);
    @Mapping(target = "tracks", source = "tracks")
    PlaylistDTO toDTO(PlaylistEntity playlistEntity);
    List<PlaylistDTO> toDTOList(List<PlaylistEntity> playlistEntities);
}

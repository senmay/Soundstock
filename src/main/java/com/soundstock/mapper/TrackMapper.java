package com.soundstock.mapper;

import com.soundstock.model.dto.SimpleTrackDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.TrackEntity;
import java.util.Collections;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface TrackMapper {
    List<TrackDTO> toDTOList(List<TrackEntity> songEntities);
    @Mapping(target = "artists", ignore = true)
    @Mapping(target = "playlists", ignore = true)
    @Mapping(target = "album", ignore = true)
    TrackDTO toDTO(TrackEntity trackEntity);
    TrackEntity toEntity(TrackDTO trackDTO);
    List<TrackEntity> toEntityList(List<TrackDTO> trackDTOS);
    // Metoda mapująca TrackEntity na SimpleTrackDTO
    @Mapping(target = "artistName", expression = "java(mapArtistsNames(trackEntity.getArtists()))")
    @Mapping(target = "albumName", source = "album.title")
    SimpleTrackDTO toSimpleDTO(TrackEntity trackEntity);
    List<SimpleTrackDTO> toSimpleDTO(List<TrackEntity> trackEntities);

    // Pomocnicza metoda do mapowania nazw artystów
    default List<String> mapArtistsNames(List<ArtistEntity> artists) {
        if (artists == null) {
            return Collections.emptyList();
        }
        return artists.stream()
                .map(ArtistEntity::getName)
                .toList();
    }
}

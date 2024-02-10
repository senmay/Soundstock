package com.soundstock.mapper;

import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.lastfm.ArtistLastFm;
import com.soundstock.model.dto.api.lastfm.TrackLastFm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper
public interface TrackLastFmMapper {
    @Mapping(source = "name", target = "title")
    @Mapping(target = "artists", expression = "java(mapArtistToList(trackLastFm.getName()))")
    @Mapping(source = "duration", target = "duration", qualifiedByName = "stringToInteger")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(source = "playcount", target = "playcount", qualifiedByName = "stringToLong")
    TrackDTO trackToTrackDTO(TrackLastFm trackLastFm);
    List<TrackDTO> trackToTrackDTOList(List<TrackLastFm> trackLastFms);
    @Named("stringToInteger")
    default Integer stringToInteger(String value) {
        return value != null ? Integer.parseInt(value) : null;
    }
    @Named("stringToLong")
    default Long stringToLong(String value) {
        return value != null ? Long.parseLong(value) : null;
    }
    default List<ArtistDTO> mapArtistToList(String artist) {
        if (artist == null) {
            return null;
        }
        ArtistDTO artistDTO = ArtistDTO.builder().name(artist).build(); // Załóżmy, że Artist ma te pola
        return Collections.singletonList(artistDTO);
    }
}

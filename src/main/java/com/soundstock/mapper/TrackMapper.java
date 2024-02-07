package com.soundstock.mapper;

import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.dto.api.lastfm.Track;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface TrackMapper {
    @Mapping(source = "name", target = "title")
    @Mapping(source = "artist", target = "artist")
    @Mapping(source = "duration", target = "duration", qualifiedByName = "stringToInteger")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(source = "playcount", target = "playcount", qualifiedByName = "stringToLong")
    SongDTO trackToSongDTO(Track track);
    List<SongDTO> trackToSongDTOList(List<Track> tracks);
    @Named("stringToInteger")
    default Integer stringToInteger(String value) {
        return value != null ? Integer.parseInt(value) : null;
    }
    @Named("stringToLong")
    default Long stringToLong(String value) {
        return value != null ? Long.parseLong(value) : null;
    }
}

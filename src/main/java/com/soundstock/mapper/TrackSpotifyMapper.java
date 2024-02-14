package com.soundstock.mapper;

import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.spotify.AlbumSpotify;
import com.soundstock.model.dto.api.spotify.ArtistSpotify;
import com.soundstock.model.dto.api.spotify.TrackSpotify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TrackSpotifyMapper {
    @Mapping(target = "duration", source = "duration_ms")
    @Mapping(target = "spotifyId", source = "spotifyId")
    TrackDTO toDTO(TrackSpotify trackSpotify);
    ArtistDTO toDTO(ArtistSpotify artistSpotify);
    AlbumDTO toDTO(AlbumSpotify album);
}

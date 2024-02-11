package com.soundstock.mapper;

import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.api.spotify.AlbumSpotify;
import com.soundstock.model.dto.api.spotify.ArtistSpotify;
import com.soundstock.model.dto.api.spotify.TrackSpotify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface TrackSpotifyMapper {
    @Mapping(target = "duration", source = "duration_ms")
    @Mapping(target = "spotifyId", source = "spotifyId")
    TrackDTO mapTrackSpotifyToTrackDTO(TrackSpotify trackSpotify);
    List<TrackDTO> mapTrackSpotifyToTrackDTOList(List<TrackSpotify> trackSpotifyList);
    ArtistDTO artistSpotifyToArtistDTO(ArtistSpotify artistSpotify);
    AlbumDTO albumSpotifyToAlbumDTO(AlbumSpotify album);
}

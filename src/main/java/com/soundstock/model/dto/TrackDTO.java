package com.soundstock.model.dto;

import lombok.Value;

import java.util.List;

@Value
public class TrackDTO {
    Long id;
    String title;
    List<ArtistDTO> artists;
    Integer duration;
    AlbumDTO album;
    Long playcount;
    Integer popularity;
    List<PlaylistDTO> playlists;
    String spotifyId;

}

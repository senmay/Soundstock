package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
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

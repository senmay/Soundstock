package com.soundstock.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"artists", "playlists"})
public class TrackDTO {
    Long id;
    String title;
    List<ArtistDTO> artists;
    Long duration;
    AlbumDTO album;
    Long playcount;
    Integer popularity;
    List<PlaylistDTO> playlists;
    String spotifyId;
}

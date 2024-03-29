package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrackSpotify {
    @JsonProperty("id")
    private String spotifyId;
    @JsonProperty("name")
    private String title;
    private String popularity;
    private String duration_ms;
    private List<ArtistSpotify> artists;
    private AlbumSpotify album;

}

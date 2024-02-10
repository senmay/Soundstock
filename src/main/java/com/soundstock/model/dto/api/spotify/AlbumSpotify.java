package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class AlbumSpotify {
    @JsonProperty("id")
    private String spotifyId;
    @JsonProperty("name")
    private String title;
    private String release_date;
    private String total_tracks;
    private List<ImageSpotify> images;
}

package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaylistResponse {
    private String description;
    private Followers followers;
    @JsonProperty("id")
    private String spotifyId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("tracks")
    private TracksSpotify tracks;
}

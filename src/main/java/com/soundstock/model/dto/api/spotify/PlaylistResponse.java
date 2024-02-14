package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.K;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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



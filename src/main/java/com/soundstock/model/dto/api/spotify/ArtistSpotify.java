package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ArtistSpotify {
    @JsonProperty("id")
    private String spotifyId;
    private String name;
}

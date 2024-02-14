package com.soundstock.model.dto.api.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TracksResponse {
    private List<TrackInfo> tracks;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackInfo {
        @JsonProperty("id")
        private String spotifyId;
        private String name;
        private Integer popularity;
    }
}
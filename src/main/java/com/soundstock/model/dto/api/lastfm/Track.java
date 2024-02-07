package com.soundstock.model.dto.api.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private String name;
    private String duration;
    private String playcount;
    private String listeners;
    private String url;
    private Artist artist;
}

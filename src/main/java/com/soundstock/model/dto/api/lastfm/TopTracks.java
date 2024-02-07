package com.soundstock.model.dto.api.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopTracks {
    private List<Track> track;
}

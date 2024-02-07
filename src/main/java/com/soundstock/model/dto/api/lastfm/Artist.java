package com.soundstock.model.dto.api.lastfm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {
    private String name;
}

package com.soundstock.enums;

import lombok.Getter;

@Getter
public enum LastFmEndpoints {
    MOST_POPULAR_SONGS("?method=chart.gettoptracks&"),
    LAST_FM_BASEURL("https://ws.audioscrobbler.com/2.0/");

    private final String endpoint;

    LastFmEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }
}

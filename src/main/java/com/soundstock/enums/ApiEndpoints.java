package com.soundstock.enums;

import lombok.Getter;

@Getter
public enum ApiEndpoints {
    LAST_FM_GET_MOST_POPULAR_SONGS("https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&"),
    COINGECKO_GET_COINLIST("https://api.coingecko.com/api/v3//coins/list/");

    private final String endpoint;

    ApiEndpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return endpoint;
    }
}

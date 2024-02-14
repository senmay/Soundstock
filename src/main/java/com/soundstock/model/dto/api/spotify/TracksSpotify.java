package com.soundstock.model.dto.api.spotify;

import lombok.Data;

import java.util.List;

@Data
public class TracksSpotify {
    private List<ItemSpotify> items;
}

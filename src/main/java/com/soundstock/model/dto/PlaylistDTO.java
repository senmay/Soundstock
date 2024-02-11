package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PlaylistDTO {
    Long id;
    String name;
    List<TrackDTO> tracks;
    Long followers;
    public void addTrack(TrackDTO track){
        if (tracks == null){
            tracks = new ArrayList<>();
        }
        tracks.add(track);        ;
    }
}

package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaylistDTO {
    Long id;
    String name;
    List<TrackDTO> tracks;
}

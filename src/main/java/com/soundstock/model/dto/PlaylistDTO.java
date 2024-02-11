package com.soundstock.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PlaylistDTO {
    Long id;
    String name;
    List<SimpleTrackDTO> tracks;
    Long followers;
}

package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SimpleTrackDTO {
    private Long id;
    private String title;
    private List<String> artistName;
}

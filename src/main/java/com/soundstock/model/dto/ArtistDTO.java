package com.soundstock.model.dto;

import lombok.Value;

import java.util.List;

@Value
public class ArtistDTO {
    private Long id;
    private String name;
    private String image;
    private List<AlbumDTO> album;
}

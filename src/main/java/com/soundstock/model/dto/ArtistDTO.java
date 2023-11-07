package com.soundstock.model.dto;

import lombok.Value;

import java.util.List;

@Value
public class ArtistDTO {
    Long id;
    String name;
    String image;
    List<AlbumDTO> albums;
}

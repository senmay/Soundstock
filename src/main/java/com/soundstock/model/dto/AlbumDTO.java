package com.soundstock.model.dto;

import lombok.Value;

import java.util.List;

@Value
public class AlbumDTO {
    Long id;
    String title;
    ArtistDTO artist;
    String genre;
    Integer year;
    List<SongDTO> songs;
    String image;
    String description;
}

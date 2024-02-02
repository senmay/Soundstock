package com.soundstock.model.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

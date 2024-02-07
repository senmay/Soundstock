package com.soundstock.model.dto;

import lombok.Value;

@Value
public class SongDTO {
    Long id;
    String title;
    ArtistDTO artist;
    Integer duration;
    AlbumDTO album;
    Long playcount;

}

package com.soundstock.model.dto;

import com.soundstock.model.Album;
import lombok.Value;

@Value
public class SongDTO {
    Long id;
    String title;
    ArtistDTO artist;
    Integer duration;
    Album album;

}

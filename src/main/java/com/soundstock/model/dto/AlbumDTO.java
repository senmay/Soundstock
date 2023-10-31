package com.soundstock.model.dto;

import com.soundstock.model.Song;
import lombok.Value;

import java.util.List;

@Value
public class AlbumDTO {
    Long id;
    String title;
    String artist;
    String genre;
    String year;
    List<SongDTO> songs;


}

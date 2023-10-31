package com.soundstock.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Album {
    private Long id;
    private String title;
    private String artist;
    private String genre;
    private String year;
    private List<Song> songs;
    private String image;
    private String description;



}

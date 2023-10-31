package com.soundstock.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Song {
    private Long id;
    private String title;
    private String artist;
    private Integer duration;
    private Album album;

}

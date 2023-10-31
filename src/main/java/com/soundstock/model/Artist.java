package com.soundstock.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Artist {
    private Long id;
    private String name;
    private String image;
    private List<Album> albums;
}

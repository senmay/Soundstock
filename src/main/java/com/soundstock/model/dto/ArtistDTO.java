package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ArtistDTO {
    Long id;
    String name;
    String image;
    List<AlbumDTO> albums;
    String spotifyId;
}

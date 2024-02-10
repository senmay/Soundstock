package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Builder
public class ArtistDTO {
    Long id;
    String name;
    String image;
    List<AlbumDTO> albums;
    String spotifyId;
}

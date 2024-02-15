package com.soundstock.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(value = {"albums"})
public class ArtistDTO {
    Long id;
    String name;
    String image;
    List<AlbumDTO> albums;
    String spotifyId;
}


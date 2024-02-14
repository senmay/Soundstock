package com.soundstock.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soundstock.model.dto.api.spotify.ImageSpotify;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"artist"})
public class AlbumDTO {
    Long id;
    String title;
    ArtistDTO artist;
    Integer year;
    List<TrackDTO> tracks;
    String image;
    String description;
    String spotifyId;
    String release_date;
    String total_tracks;
}

package com.soundstock.model.dto;

import com.soundstock.model.dto.api.spotify.ImageSpotify;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    Long id;
    String title;
    ArtistDTO artist;
    String genre;
    Integer year;
    List<TrackDTO> tracks;
    String image;
    String description;
    String spotifyId;
    String release_date;
    String total_tracks;
}

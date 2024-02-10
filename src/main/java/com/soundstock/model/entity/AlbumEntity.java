package com.soundstock.model.entity;


import com.soundstock.model.dto.api.spotify.ImageSpotify;
import jakarta.persistence.*;
import lombok.*;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "albums")
public class AlbumEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private ArtistEntity artist;
    private String genre;
    private Integer year;
    @OneToMany(mappedBy = "album")
    private List<TrackEntity> songs;
    String image;
    String description;
    String spotifyId;
    String release_date;
    String total_tracks;

}

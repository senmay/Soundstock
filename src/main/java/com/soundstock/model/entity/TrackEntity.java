package com.soundstock.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class TrackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "track_artist",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private List<ArtistEntity> artists;
    private Integer duration;
    @ManyToOne(cascade = CascadeType.PERSIST,optional = true)
    @JoinColumn(name = "album_Id")
    private AlbumEntity album;
    private Long playcount;
    private Integer popularity;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "track_playlist",
            joinColumns = @JoinColumn(name = "track_id"),
            inverseJoinColumns = @JoinColumn(name = "playlist_id")
    )
    private List<PlaylistEntity> playlists;

}

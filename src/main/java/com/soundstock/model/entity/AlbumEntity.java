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
    private List<SongEntity> songs;
    String image;
    String description;

}

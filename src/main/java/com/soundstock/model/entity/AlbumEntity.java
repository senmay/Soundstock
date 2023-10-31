package com.soundstock.model.entity;


import com.soundstock.model.Artist;
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
    private Long Id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_Id")
    private ArtistEntity artist;
    private String genre;
    private String year;
    @OneToMany(mappedBy = "album")
    private List<SongEntity> songs;
    String image;
    String description;

}

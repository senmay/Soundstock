package com.soundstock.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "songs")
public class SongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name ="artist_id")
    private ArtistEntity artist;
    private Integer duration;
    @ManyToOne(optional = true)
    @JoinColumn(name = "album_Id")
    private AlbumEntity album;

}

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
    private Long Id;
    private String title;
    private String artist;
    private Integer duration;
    @ManyToOne(optional = true)
    @JoinColumn(name = "album_Id")
    private AlbumEntity album;

}

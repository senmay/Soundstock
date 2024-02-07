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
@Table(name = "artists")
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true,nullable = false)
    private String name;
    @OneToMany(mappedBy = "artist")
    private List<AlbumEntity> albums;

}

package com.soundstock.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "playlists")
public class PlaylistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    @ManyToMany(mappedBy = "playlists")
    List<TrackEntity> tracks;
    Long followers;
    public void addTrack(TrackEntity track){
        if (tracks == null){
            tracks = new ArrayList<>();
        }
        tracks.add(track);
    }
}

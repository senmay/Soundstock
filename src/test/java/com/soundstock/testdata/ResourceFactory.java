package com.soundstock.testdata;

import com.github.javafaker.Faker;
import com.soundstock.mapper.*;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.TrackEntity;

import java.util.ArrayList;
import java.util.List;

public class ResourceFactory {
    TrackMapper trackMapper = new TrackMapperImpl();
    ArtistMapper artistMapper = new ArtistMapperImpl();
    AlbumMapper albumMapper = new AlbumMapperImpl();
    Faker faker = new Faker();

    protected ArtistDTO provideArtistDTO() {
        List<AlbumDTO> albums = new ArrayList<>();
        return new ArtistDTO(
                null,
                faker.rockBand().name(),
                faker.avatar().image(),
                albums
        );
    }

    protected List<ArtistDTO> provideArtistDTOList(Integer size) {
        List<ArtistDTO> artistList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            artistList.add(provideArtistDTO());
        }
        return artistList;
    }

    protected ArtistDTO provideArtistWithNameUserOneAndId1() {
        return new ArtistDTO(
                1L, // ID
                "UserOne", // Name
                "testimage", // Image
                new ArrayList<>() // Empty list of albums
        );
    }

    protected ArtistEntity provideArtistEntity() {
        return artistMapper.mapDTOToArtistEntity(provideArtistDTO());
    }

    protected List<ArtistEntity> provideAristEntityList(Integer size) {
        List<ArtistEntity> artistEntities = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            artistEntities.add(provideArtistEntity());
        }
        return artistEntities;
    }

    protected TrackDTO provideRandomSong() {
        return new TrackDTO(
                null,
                faker.rockBand().name(),
                provideArtistDTO(),
                faker.number().numberBetween(120, 300), // Duration in seconds
                null,
                faker.number().numberBetween(1,1000000L)//temporary null for album

        );
    }

    protected List<TrackDTO> provideRandomSongList(Integer size) {
        List<TrackDTO> songList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            songList.add(provideRandomSong());
        }
        return songList;
    }

    protected TrackEntity provideSongEntity() {
        return trackMapper.mapTrackDTOtoTrackEntity(provideRandomSong());
    }

    protected List<TrackEntity> provideSongEntityList(Integer size) {
        List<TrackEntity> songEntities = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            songEntities.add(provideSongEntity());
        }
        return songEntities;
    }

    protected AlbumDTO provideAlbumDTO() {
        ArtistDTO artistDTO = provideArtistDTO();
        return new AlbumDTO(
                null,
                faker.lorem().word(),
                null,
                faker.music().genre(),
                faker.number().numberBetween(1980, 2021),
                null,
                faker.internet().image(),
                faker.lorem().sentence()
        );
    }

    protected AlbumEntity provideAlbumEntity() {
        AlbumDTO albumDTO = provideAlbumDTO();
        return albumMapper.mapDTOToAlbumEntity(albumDTO);
    }

    protected List<AlbumDTO> provideAlbumDTOList(Integer size) {
        List<AlbumDTO> albums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            albums.add(provideAlbumDTO());
        }
        return albums;
    }

    protected List<AlbumEntity> provideAlbumEntityList(Integer size) {
        List<AlbumEntity> albums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            albums.add(provideAlbumEntity());
        }
        return albums;
    }

}

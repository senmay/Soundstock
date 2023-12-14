package com.soundstock.testdata;

import com.github.javafaker.Faker;
import com.soundstock.mapper.*;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.SongEntity;

import java.util.ArrayList;
import java.util.List;

public class ResourceFactory {
    SongMapper songMapper = new SongMapperImpl();
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

    protected SongDTO provideRandomSong() {
        return new SongDTO(
                null,
                faker.rockBand().name(),
                provideArtistDTO(),
                faker.number().numberBetween(120, 300), // Duration in seconds
                null //temporary null for album
        );
    }

    protected List<SongDTO> provideRandomSongList(Integer size) {
        List<SongDTO> songList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            songList.add(provideRandomSong());
        }
        return songList;
    }

    protected SongEntity provideSongEntity() {
        return songMapper.mapSongDTOtoSongEntity(provideRandomSong());
    }

    protected List<SongEntity> provideSongEntityList(Integer size) {
        List<SongEntity> songEntities = new ArrayList<>();
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

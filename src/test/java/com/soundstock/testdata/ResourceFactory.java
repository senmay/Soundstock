package com.soundstock.testdata;

import com.github.javafaker.Faker;
import com.soundstock.mapper.*;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.PlaylistDTO;
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
        return new ArtistDTO(
                null,
                faker.rockBand().name(),
                faker.avatar().image(),
                null,
                faker.lorem().word()
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
                1L,
                "User One",
                faker.lorem().word(),
                provideAlbumDTOList(2),
                faker.lorem().word());
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

    protected TrackDTO provideRandomTrack() {
        return new TrackDTO(
                null,
                faker.lorem().word(),
               null,
                faker.number().numberBetween(180, 300),
                provideAlbumDTO(),
                faker.number().randomNumber(),
                faker.number().numberBetween(1, 100),
                providePlaylistDTOList(2),
                faker.lorem().word()
        );
    }
    protected PlaylistDTO providePlaylistDTO(){
        return new PlaylistDTO(
                null,
                faker.lorem().word(),
                null,
                100L
        );
    }
    protected List<PlaylistDTO> providePlaylistDTOList(Integer size){
        List<PlaylistDTO> playlistDTOS = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            playlistDTOS.add(providePlaylistDTO());
        }
        return playlistDTOS;
    }

    protected List<TrackDTO> provideRandomTrackList(Integer size) {
        List<TrackDTO> songList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            songList.add(provideRandomTrack());
        }
        return songList;
    }

    protected TrackEntity provideTrackEntity() {
        return trackMapper.mapTrackDTOtoTrackEntity(provideRandomTrack());
    }

    protected List<TrackEntity> provideTrackEntityList(Integer size) {
        List<TrackEntity> songEntities = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            songEntities.add(provideTrackEntity());
        }
        return songEntities;
    }

    protected AlbumDTO provideAlbumDTO() {
        ArtistDTO artistDTO = provideArtistDTO();
        return new AlbumDTO(
                null,
                faker.lorem().word(),
                artistDTO,
                faker.lorem().word(),
                faker.number().numberBetween(1900, 2021),
               null,
                faker.avatar().image(),
                faker.lorem().word(),
                faker.lorem().word(),
                faker.lorem().word(),
                faker.lorem().word()
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

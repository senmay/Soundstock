package com.soundstock.testdata;

import com.github.javafaker.Faker;
import com.soundstock.mapper.SongMapper;
import com.soundstock.mapper.SongMapperImpl;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.SongEntity;

import java.util.ArrayList;
import java.util.List;

public interface SongProvider extends ArtistProvider {
    SongMapper songMapper = new SongMapperImpl();
    Faker faker = new Faker();

    default SongDTO provideRandomSong() {
        return new SongDTO(
                faker.number().randomNumber(),
                faker.rockBand().name(),
                provideArtistDTO(),
                faker.number().numberBetween(120, 300), // Duration in seconds
                null //temporary null for album
        );
    }

    default List<SongDTO> provideRandomSongList(Integer size) {
        List<SongDTO> songList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            songList.add(provideRandomSong());
        }
        return songList;
    }

    default SongEntity provideSongEntity(){
        return songMapper.mapSongDTOtoSongEntity(provideRandomSong());
    }

    default List<SongEntity> provideSongEntityList(Integer size){
        List<SongEntity> songEntities = new ArrayList<>();
        for (int i = 0; i < size; i++){
            songEntities.add(provideSongEntity());
        }
        return songEntities;
    }
}


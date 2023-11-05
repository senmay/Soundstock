package com.soundstock.testdata;

import com.github.javafaker.Faker;
import com.soundstock.mapper.ArtistMapper;
import com.soundstock.mapper.ArtistMapperImpl;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;

import java.util.ArrayList;
import java.util.List;

public interface ArtistProvider {
    ArtistMapper artistMapper = new ArtistMapperImpl();
    Faker faker = new Faker();

    default ArtistDTO provideArtistDTO() {
        List<AlbumDTO> albums = new ArrayList<>();
        return new ArtistDTO(
                faker.number().numberBetween(1L, 100L),
                faker.rockBand().name(),
                faker.avatar().image(),
                albums
        );
    }

    default List<ArtistDTO> provideArtistDTOList(Integer size) {
        List<ArtistDTO> artistList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            artistList.add(provideArtistDTO());
        }
        return artistList;
    }
    default ArtistDTO provideArtistWithNameUserOneAndId1() {
        return new ArtistDTO(
                1L, // ID
                "UserOne", // Name
                "testimage", // Image
                new ArrayList<>() // Empty list of albums
        );
    }
    default ArtistEntity provideArtistEntity(){
        return artistMapper.mapDTOToArtistEntity(provideArtistDTO());
    }
    default List<ArtistEntity> provideAristEntityList(Integer size){
        List<ArtistEntity> artistEntities = new ArrayList<>();
        for (int i = 0; i < size; i++){
            artistEntities.add(provideArtistEntity());
        }
        return artistEntities;
    }
}

package com.soundstock.mappers;

import com.soundstock.mapper.AlbumMapper;
import com.soundstock.mapper.AlbumMapperImpl;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.testdata.ResourceFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumMapperTests extends ResourceFactory {
    AlbumMapper albumMapper = new AlbumMapperImpl();
    @Test
    public void test_mapping_album_entity_to_dto_with_all_fields_set() {
        // Arrange
        AlbumEntity albumEntity = AlbumEntity.builder()
                .id(1L)
                .title("Album Title")
                .artist(ArtistEntity.builder().id(1L).name("Artist Name").build())
                .genre("Rock")
                .year(2022)
                .songs(new ArrayList<>())
                .image("album_image.jpg")
                .description("Album Description")
                .build();

        // Act
        AlbumDTO albumDTO = albumMapper.mapAlbumEntityToDTO(albumEntity);

        // Assert
        assertEquals(albumEntity.getId(), albumDTO.getId());
        assertEquals(albumEntity.getTitle(), albumDTO.getTitle());
        assertEquals(albumEntity.getArtist().getId(), albumDTO.getArtist().getId());
        assertEquals(albumEntity.getArtist().getName(), albumDTO.getArtist().getName());
        assertEquals(albumEntity.getGenre(), albumDTO.getGenre());
        assertEquals(albumEntity.getYear(), albumDTO.getYear());
        assertEquals(albumEntity.getSongs().size(), albumDTO.getSongs().size());
        assertEquals(albumEntity.getImage(), albumDTO.getImage());
        assertEquals(albumEntity.getDescription(), albumDTO.getDescription());
    }

    // Mapping a list of AlbumEntities to a list of AlbumDTOs should return a list of AlbumDTOs with the same values for all fields.
    @Test
    public void test_mapping_list_of_album_entities_to_list_of_album_dtos() {
        // Arrange
        List<AlbumEntity> albumEntities = new ArrayList<>();
        albumEntities.add(AlbumEntity.builder()
                .id(1L)
                .title("Album 1")
                .artist(ArtistEntity.builder().id(1L).name("Artist 1").build())
                .genre("Rock")
                .year(2022)
                .songs(new ArrayList<>())
                .image("album_image_1.jpg")
                .description("Album 1 Description")
                .build());
        albumEntities.add(AlbumEntity.builder()
                .id(2L)
                .title("Album 2")
                .artist(ArtistEntity.builder().id(2L).name("Artist 2").build())
                .genre("Pop")
                .year(2023)
                .songs(new ArrayList<>())
                .image("album_image_2.jpg")
                .description("Album 2 Description")
                .build());

        // Act
        List<AlbumDTO> albumDTOs = albumMapper.mapAlbumEntitesToDTOList(albumEntities);

        // Assert
        assertEquals(albumEntities.size(), albumDTOs.size());
        for (int i = 0; i < albumEntities.size(); i++) {
            AlbumEntity albumEntity = albumEntities.get(i);
            AlbumDTO albumDTO = albumDTOs.get(i);
            assertEquals(albumEntity.getId(), albumDTO.getId());
            assertEquals(albumEntity.getTitle(), albumDTO.getTitle());
            assertEquals(albumEntity.getArtist().getId(), albumDTO.getArtist().getId());
            assertEquals(albumEntity.getArtist().getName(), albumDTO.getArtist().getName());
            assertEquals(albumEntity.getGenre(), albumDTO.getGenre());
            assertEquals(albumEntity.getYear(), albumDTO.getYear());
            assertEquals(albumEntity.getSongs().size(), albumDTO.getSongs().size());
            assertEquals(albumEntity.getImage(), albumDTO.getImage());
            assertEquals(albumEntity.getDescription(), albumDTO.getDescription());
        }
    }
    // Mapping a null AlbumEntity to an AlbumDTO should return null.
    @Test
    public void test_mapping_null_album_entity_to_dto() {
        // Arrange
        AlbumEntity albumEntity = null;

        // Act
        AlbumDTO albumDTO = albumMapper.mapAlbumEntityToDTO(albumEntity);

        // Assert
        assertNull(albumDTO);
    }

    // Mapping a null AlbumDTO to an AlbumEntity should return null.
    @Test
    public void test_mapping_null_album_dto_to_entity() {
        // Arrange
        AlbumDTO albumDTO = null;

        // Act
        AlbumEntity albumEntity = albumMapper.mapDTOToAlbumEntity(albumDTO);

        // Assert
        assertNull(albumEntity);
    }

    // Mapping an AlbumDTO to an AlbumEntity with all fields set should return an AlbumEntity with the same values for all fields.
    @Test
    public void test_mapping_album_dto_to_entity_with_all_fields_set() {

        // Arrange
        ArtistDTO artistDTO = new ArtistDTO(1L, "Artist Name", null, null);
        List<TrackDTO> songs = new ArrayList<>();
        AlbumDTO albumDTO = provideAlbumDTO();
        albumDTO.setArtist(artistDTO);
        albumDTO.setSongs(songs);

        // Act
        AlbumEntity albumEntity = albumMapper.mapDTOToAlbumEntity(albumDTO);

        // Assert
        assertEquals(albumDTO.getId(), albumEntity.getId());
        assertEquals(albumDTO.getTitle(), albumEntity.getTitle());
        assertEquals(albumDTO.getArtist().getId(), albumEntity.getArtist().getId());
        assertEquals(albumDTO.getArtist().getName(), albumEntity.getArtist().getName());
        assertEquals(albumDTO.getGenre(), albumEntity.getGenre());
        assertEquals(albumDTO.getYear(), albumEntity.getYear());
        assertEquals(albumDTO.getSongs().size(), albumEntity.getSongs().size());
    }

}

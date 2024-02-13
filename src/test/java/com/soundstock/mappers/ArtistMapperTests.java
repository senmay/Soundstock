package com.soundstock.mappers;

import com.soundstock.mapper.ArtistMapper;
import com.soundstock.mapper.ArtistMapperImpl;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.testdata.ResourceFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistMapperTests extends ResourceFactory {
    ArtistMapper artistMapper = new ArtistMapperImpl();
    @Test
    public void test_mapArtistEntityToDTO() {
        // Arrange
        ArtistEntity artistEntity = ArtistEntity.builder()
                .id(1L)
                .name("John Doe")
                .build();

        // Act
        ArtistDTO artistDTO = artistMapper.toDTO(artistEntity);

        // Assert
        assertEquals(artistEntity.getId(), artistDTO.getId());
        assertEquals(artistEntity.getName(), artistDTO.getName());
        assertNull(artistDTO.getImage());
        assertNull(artistDTO.getAlbums());
    }
    // Should correctly map a list of ArtistEntities to a list of ArtistDTOs
    @Test
    public void test_mapArtistEntitesToDTOList() {
        // Arrange
        List<ArtistEntity> artistEntities = new ArrayList<>();
        artistEntities.add(ArtistEntity.builder()
                .id(1L)
                .name("John Doe")
                .build());
        artistEntities.add(ArtistEntity.builder()
                .id(2L)
                .name("Jane Smith")
                .build());

        // Act
        List<ArtistDTO> artistDTOs = artistMapper.toDTOList(artistEntities);

        // Assert
        assertEquals(artistEntities.size(), artistDTOs.size());
        for (int i = 0; i < artistEntities.size(); i++) {
            ArtistEntity artistEntity = artistEntities.get(i);
            ArtistDTO artistDTO = artistDTOs.get(i);
            assertEquals(artistEntity.getId(), artistDTO.getId());
            assertEquals(artistEntity.getName(), artistDTO.getName());
            assertNull(artistDTO.getImage());
            assertNull(artistDTO.getAlbums());
        }
    }
    // Should handle empty list input for mapArtistEntitesToDTOList method
    @Test
    public void test_mapArtistEntitesToDTOList_emptyList() {
        // Arrange
        List<ArtistEntity> artistEntities = new ArrayList<>();

        // Act
        List<ArtistDTO> artistDTOs = artistMapper.toDTOList(artistEntities);

        // Assert
        assertNotNull(artistDTOs);
        assertTrue(artistDTOs.isEmpty());
    }
    // Should handle empty albums list for ArtistEntity
    @Test
    public void test_mapArtistEntityToDTO_emptyAlbums() {
        // Arrange
        ArtistEntity artistEntity = provideArtistEntity();

        // Act
        ArtistDTO artistDTO = artistMapper.toDTO(artistEntity);

        // Assert
        assertEquals(artistEntity.getId(), artistDTO.getId());
        assertEquals(artistEntity.getName(), artistDTO.getName());
        assertNull(artistDTO.getImage());
        assertNotNull(artistDTO.getAlbums());
        assertTrue(artistDTO.getAlbums().isEmpty());
    }
    // Should handle null albums list for ArtistEntity
    @Test
    public void test_mapArtistEntityToDTO_nullAlbums() {
        // Arrange
        ArtistEntity artistEntity = provideArtistEntity();

        // Act
        ArtistDTO artistDTO = artistMapper.toDTO(artistEntity);

        // Assert
        assertEquals(artistEntity.getId(), artistDTO.getId());
        assertEquals(artistEntity.getName(), artistDTO.getName());
        assertNull(artistDTO.getImage());
        assertNull(artistDTO.getAlbums());
    }
}

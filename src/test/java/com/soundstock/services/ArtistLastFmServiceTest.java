package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.ArtistMapper;
import com.soundstock.mapper.ArtistMapperImpl;
import com.soundstock.model.dto.ArtistDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.repository.ArtistRepository;
import com.soundstock.testdata.ResourceFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ArtistLastFmServiceTest extends ResourceFactory {
    @InjectMocks
    private ArtistService artistService;
    @Mock
    private ArtistRepository artistRepository;
    @Spy
    private ArtistMapper artistMapper = new ArtistMapperImpl();

    @Test
    public void test_getAllArtists() {
        // Given
        List<ArtistEntity> artistEntities = provideAristEntityList(3);
        when(artistRepository.findAll()).thenReturn(artistEntities);

        // When
        List<ArtistDTO> result = artistService.getAllArtists();

        // Then
        Assertions.assertEquals(3, result.size());
        for (int i = 0; i < result.size(); i++) {
            ArtistDTO artistDTO = result.get(i);
            ArtistEntity artistEntity = artistEntities.get(i);

            Assertions.assertEquals(artistEntity.getId(), artistDTO.getId());
            Assertions.assertEquals(artistEntity.getName(), artistDTO.getName());
        }
    }

    // getArtistById returns the artist with the given id
    @Test
    public void test_getArtistById() {
        // Given
        Long id = 1L;
        ArtistEntity artistEntity = provideArtistEntity();
        artistEntity.setId(2L);
        when(artistRepository.findById(id)).thenReturn(Optional.of(artistEntity));

        // When
        ArtistDTO result = artistService.getArtistById(id);

        // Then
        Assertions.assertEquals(artistEntity.getId(), result.getId());
        Assertions.assertEquals(artistEntity.getName(), result.getName());
    }

    // getArtistByName returns the artist with the given name
    @Test
    public void test_getArtistByName() {
        // Given
        String name = "Artist";
        ArtistEntity artistEntity = provideArtistEntity();
        artistEntity.setName(name);
        when(artistRepository.findByName(name)).thenReturn(Optional.of(artistEntity));

        // When
        ArtistDTO result = artistService.getArtistByName(name);

        // Then
        Assertions.assertEquals(artistEntity.getId(), result.getId());
        Assertions.assertEquals(artistEntity.getName(), result.getName());
    }

    // getArtistByNameOptional returns an optional artist with the given name
    @Test
    public void test_getArtistByNameOptional() {
        // Given
        String name = "Artist";
        ArtistEntity artistEntity = provideArtistEntity();
        artistEntity.setName(name);
        when(artistRepository.findByName(name)).thenReturn(Optional.of(artistEntity));

        // When
        Optional<ArtistEntity> result = artistService.getArtistByNameOptional(name);

        // Then
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(artistEntity.getId(), result.get().getId());
        Assertions.assertEquals(artistEntity.getName(), result.get().getName());
    }

    // addArtist adds a new artist to the database
    @Test
    public void test_addArtist() {
        // Given
        ArtistDTO artistDTO = provideArtistDTO();
        ArtistEntity artistEntity = artistMapper.mapDTOToArtistEntity(artistDTO);
        when(artistRepository.findByName(artistDTO.getName())).thenReturn(Optional.empty());
        when(artistRepository.save(artistEntity)).thenReturn(artistEntity);

        // When
        ArtistEntity result = artistService.addArtist(artistDTO);

        // Then
        Assertions.assertEquals(artistEntity.getId(), result.getId());
        Assertions.assertEquals(artistEntity.getName(), result.getName());
    }

    // getArtistById throws ObjectNotFound if the id doesn't exist in the database
    @Test
    public void test_getArtistById_ObjectNotFound() {
        // Given
        Long id = 1L;
        when(artistRepository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThrows(ObjectNotFound.class, () -> artistService.getArtistById(id));
    }

    // getArtistByName throws ObjectNotFound if the name doesn't exist in the database
    @Test
    public void test_getArtistByName_ObjectNotFound() {
        // Given
        String name = "Artist";
        when(artistRepository.findByName(name)).thenReturn(Optional.empty());

        // Then
        assertThrows(ObjectNotFound.class, () -> artistService.getArtistByName(name));
    }

    // addArtist throws ObjectNotFound if the artist already exists in the database
    @Test
    public void test_addArtist_ObjectNotFound() {
        // Given
        ArtistDTO artistDTO = provideArtistDTO();
        ArtistEntity artistEntity = artistMapper.mapDTOToArtistEntity(artistDTO);
        when(artistRepository.findByName(artistDTO.getName())).thenReturn(Optional.of(artistEntity));

        // Then
        assertThrows(ObjectNotFound.class, () -> artistService.addArtist(artistDTO));
    }
}


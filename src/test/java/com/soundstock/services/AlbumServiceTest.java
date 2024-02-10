package com.soundstock.services;

import com.soundstock.mapper.AlbumMapper;
import com.soundstock.mapper.AlbumMapperImpl;
import com.soundstock.model.dto.AlbumDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.repository.AlbumRepository;
import com.soundstock.testdata.ResourceFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest extends ResourceFactory {
    @InjectMocks
    private AlbumService albumService;
    @Spy
    private AlbumMapper albumMapper = new AlbumMapperImpl();
    @Mock
    private AlbumRepository albumRepository;

    // createAlbum method successfully creates an album and returns "Album created"
//    @Test
//    void test_create_album_success() {
//        // Given
//        AlbumDTO albumDTO = provideAlbumDTO();
//        // When
//        String result = albumService.addAlbum(albumDTO);
//
//        // Then
//        assertEquals("Album added", result);
//        Mockito.verify(albumRepository, times(1)).save(any(AlbumEntity.class));
//    }

    // getAlbums method successfully retrieves a list of all albums and maps them to DTOs
    @Test
    void test_get_albums_success() {
        // Given
        int expectedSize = 10;
        List<AlbumEntity> albumEntities = provideAlbumEntityList(expectedSize);
        when(albumRepository.findAll()).thenReturn(albumEntities);

        // When
        List<AlbumDTO> result = albumService.getAlbums();

        // Then
        assertEquals(expectedSize, result.size());

        for (int i = 0; i < expectedSize; i++) {
            AlbumDTO albumDTO = result.get(i);
            AlbumEntity albumEntity = albumEntities.get(i);

            assertEquals(albumEntity.getId(), albumDTO.getId());
            assertEquals(albumEntity.getTitle(), albumDTO.getTitle());
            assertEquals(albumEntity.getGenre(), albumDTO.getGenre());
            assertEquals(albumEntity.getYear(), albumDTO.getYear());
            assertEquals(albumEntity.getImage(), albumDTO.getImage());
            assertEquals(albumEntity.getDescription(), albumDTO.getDescription());
        }
    }
}


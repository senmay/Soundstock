package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.SongMapper;
import com.soundstock.mapper.SongMapperImpl;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.SongEntity;
import com.soundstock.repository.SongRepository;
import com.soundstock.testdata.SongProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest implements SongProvider {
    //TODO run mapper tests before running this class

    private SongService songService;
    @Mock
    private SongRepository songRepository;
    @Mock
    private ArtistService artistService;
    private SongMapper songMapper = new SongMapperImpl();

    @BeforeEach
    void setUp() {
        // Inject mocks into SongService
        songService = new SongService(songRepository, songMapper, artistService);
    }


    @Test
    public void test_getAllSongs() {
        // Given
        List<SongEntity> songEntities = provideSongEntityList(3);
        when(songRepository.findAll()).thenReturn(songEntities);

        // When
        List<SongDTO> result = songService.getAllSongs();

        // Then
        assertEquals(3, result.size());
        for (int i = 0; i < result.size(); i++) {
            SongDTO songDTO = result.get(i);
            SongEntity songEntity = songEntities.get(i);

            assertEquals(songEntity.getId(), songDTO.getId());
            assertEquals(songEntity.getTitle(), songDTO.getTitle());
            assertEquals(songEntity.getDuration(), songDTO.getDuration());
        }
    }

    @Test
    public void test_valid_song_title() {
        // Given
        String title = "Valid Song Title";
        SongEntity songEntity = provideSongEntity();
        songEntity.setTitle(title);
        SongDTO expectedSongDTO = songMapper.mapSongEntityToSongDTO(songEntity);
        when(songRepository.findByTitle(title)).thenReturn(Optional.of(songEntity));

        // When
        SongDTO result = songService.getSongByTitle(title);

        // Then
        assertEquals(expectedSongDTO, result);
    }

    // Should throw ObjectNotFound when given a non-existent song title
    @Test
    public void test_nonexistent_song_title() {
        // Given
        String title = "Nonexistent Song Title";
        when(songRepository.findByTitle(title)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ObjectNotFound.class, () -> songService.getSongByTitle(title));
    }

    @Test
    public void test_addSong_validSongDTO() {
        // Given
        SongDTO songDTO = provideRandomSong();
        when(songRepository.findByTitleAndArtist_Name(songDTO.getTitle(), songDTO.getArtist().getName())).thenReturn(Optional.empty());

        // When
        String result = songService.addSong(songDTO);

        // Then
        assertEquals("Song added", result);
        verify(songRepository).save(any(SongEntity.class));
    }

    @Test
    public void test_addSong_existingSong() {
        // Given
        SongDTO songDTO = provideRandomSong();
        SongEntity songEntity = songMapper.mapSongDTOtoSongEntity(songDTO);
        when(songRepository.findByTitleAndArtist_Name(songDTO.getTitle(), songDTO.getArtist().getName())).thenReturn(Optional.of(songEntity));

        // When/Then
        assertThrows(ObjectNotFound.class, () -> songService.addSong(songDTO));
        verify(songRepository, times(0)).save(songEntity);
    }

    @Test
    public void test_addSong_songAlreadyExists() {
        // Given
        SongDTO songDTO = provideRandomSong();
        SongEntity existingSongEntity = songMapper.mapSongDTOtoSongEntity(songDTO);
        Optional<SongEntity> existingSong = Optional.of(existingSongEntity);
        when(songRepository.findByTitleAndArtist_Name(songDTO.getTitle(), songDTO.getArtist().getName())).thenReturn(existingSong);

        // When
        Throwable exception = assertThrows(ObjectNotFound.class, () -> songService.addSong(songDTO));

        // Then
        assertTrue(exception.getMessage().contains("Song already in database"));
        verify(songRepository, never()).save(any(SongEntity.class));
    }
}

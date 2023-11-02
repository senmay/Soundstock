package com.soundstock.services;

import com.soundstock.mapper.SongMapper;
import com.soundstock.mapper.SongMapperImpl;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.SongEntity;
import com.soundstock.repository.SongRepository;
import com.soundstock.testdata.SongProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class SongServiceTest implements SongProvider{
    @InjectMocks
    private SongService songService;
    @Mock
    private SongRepository songRepository;
    private SongMapper songMapper = new SongMapperImpl();

    @BeforeEach
    void setUp() {
        // Inject mocks into SongService
        songService = new SongService(songRepository, songMapper);    }


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

//    // Should return a SongDTO when getSongByTitle() is called with an existing song title
//    @Test
//    public void test_getSongByTitle_existingTitle() {
//        // Given
//        String title = "Song 1";
//        SongEntity songEntity = new SongEntity(1L, title, new ArtistEntity(1L, "Artist 1", "image1", new ArrayList<>()), 180, null);
//        when(songRepository.findByTitle(title)).thenReturn(Optional.of(songEntity));
//
//        // When
//        SongDTO result = songService.getSongByTitle(title);
//
//        // Then
//        assertEquals(title, result.getTitle());
//        assertEquals("Artist 1", result.getArtist().getName());
//        assertEquals(180, result.getDuration().intValue());
//    }
//
//    // Should add a new song to the database when addSong() is called with a new SongDTO
//    @Test
//    public void test_addSong_newSong() {
//        // Given
//        SongDTO songDTO = new SongDTO(1L, "New Song", new ArtistDTO(1L, "Artist 1", "image1", new ArrayList<>()), 200, null);
//        SongEntity songEntity = new SongEntity(1L, "New Song", new ArtistEntity(1L, "Artist 1", "image1", new ArrayList<>()), 200, null);
//        when(songMapper.mapSongDTOtoSongEntity(songDTO)).thenReturn(songEntity);
//        when(songRepository.findByTitleAndArtist_Name(songDTO.getTitle(), songDTO.getArtist().getName())).thenReturn(Optional.empty());
//
//        // When
//        String result = songService.addSong(songDTO);
//
//        // Then
//        assertEquals("Song added", result);
//        verify(songRepository).save(songEntity);
//    }
//
//    // Should delete a song from the database when deleteSong() is called with an existing song id
//    @Test
//    public void test_deleteSong_existingId() {
//        // Given
//        Long id = 1L;
//
//        // When
//        songService.deleteSong(id);
//
//        // Then
//        verify(songRepository).deleteById(id);
//    }
//
//    // Should delete all songs from the database when deleteAllSongs() is called
//    @Test
//    public void test_deleteAllSongs() {
//        // When
//        songService.deleteAllSongs();
//
//        // Then
//        verify(songRepository).deleteAll();
//    }
//
//    // Should throw ObjectNotFound exception when getSongByTitle() is called with a non-existing song title
//    @Test
//    public void test_getSongByTitle_nonExistingTitle() {
//        // Given
//        String title = "Non-existing Song";
//        when(songRepository.findByTitle(title)).thenReturn(Optional.empty());
//
//        // When
//        assertThrows(ObjectNotFound.class, () -> songService.getSongByTitle(title));
//    }
//
//    // Should throw ObjectNotFound exception when addSong() is called with an existing song title and artist name
//    @Test
//    public void test_addSong_existingTitleAndArtist() {
//        // Given
//        SongDTO songDTO = new SongDTO(1L, "Existing Song", new ArtistDTO(1L, "Artist 1", "image1", new ArrayList<>()), 200, null);
//        SongEntity songEntity = new SongEntity(1L, "Existing Song", new ArtistEntity(1L, "Artist 1", "image1", new ArrayList<>()), 200, null);
//        when(songMapper.mapSongDTOtoSongEntity(songDTO)).thenReturn(songEntity);
//        when(songRepository.findByTitleAndArtist_Name(songDTO.getTitle(), songDTO.getArtist().getName())).thenReturn(Optional.of(songEntity));
//
//        // When
//        assertThrows(ObjectNotFound.class, () -> songService.addSong(songDTO));
//    }
//
//    // Should delete nothing when deleteSong() is called with a non-existing song id
//    @Test
//    public void test_deleteSong_nonExistingId() {
//        // Given
//        Long id = 1L;
//        doThrow(EmptyResultDataAccessException.class).when(songRepository).deleteById(id);
//
//        // When
//        songService.deleteSong(id);
//
//        // Then
//        verify(songRepository).deleteById(id);
//    }
//
//    // Should delete all songs from the database when deleteAllSongs() is called and there are no songs in the database
//    @Test
//    public void test_deleteAllSongs_noSongs() {
//        // Given
//        when(songRepository.findAll()).thenReturn(Collections.emptyList());
//
//        // When
//        songService.deleteAllSongs();
//
//        // Then
//        verify(songRepository, never()).deleteAll();
//    }
//
//    // Should return an empty list when getAllSongs() is called and there are no songs in the database
//    @Test
//    public void test_getAllSongs_noSongs() {
//        // Given
//        when(songRepository.findAll()).thenReturn(Collections.emptyList());
//
//        // When
//        List<SongDTO> result = songService.getAllSongs();
//
//        // Then
//        assertTrue(result.isEmpty());
//    }
//
//    // Should throw ObjectNotFound exception when addSong() is called with a null SongDTO
//    @Test
//    public void test_addSong_nullSongDTO() {
//        // Given
//        SongDTO songDTO = null;
//
//        // When
//        assertThrows(ObjectNotFound.class, () -> songService.addSong(songDTO));
//    }
//
//    // Should throw ObjectNotFound exception when addSong() is called with a SongDTO with null title or artist
//    @Test
//    public void test_addSong_nullTitleOrArtist() {
//        // Given
//        SongDTO songDTO1 = new SongDTO(1L, null, new ArtistDTO(1L, "Artist 1", "image1", new ArrayList<>()), 200, null);
//        SongDTO songDTO2 = new SongDTO(1L, "New Song", new ArtistDTO(1L, null, "image1", new ArrayList<>()), 200, null);
//
//        // When & Then
//        assertThrows(ObjectNotFound.class, () -> songService.addSong(songDTO1));
//        assertThrows(ObjectNotFound.class, () -> songService.addSong(songDTO2));
//    }

}
package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.TrackMapper;
import com.soundstock.mapper.TrackMapperImpl;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.repository.TrackRepository;
import com.soundstock.testdata.ResourceFactory;
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
class TrackLastFmServiceTest extends ResourceFactory {
    private TrackService trackService;
    @Mock
    private TrackRepository trackRepository;
    @Mock
    private ArtistService artistService;
    private final TrackMapper trackMapper = new TrackMapperImpl();

//    @BeforeEach
//    void setUp() {
//        // Inject mocks into SongService
//        trackService = new TrackService(trackRepository, trackMapper, artistService);
//    }


    @Test
    void test_getAllSongs() {
        // Given
        List<TrackEntity> songEntities = provideTrackEntityList(3);
        when(trackRepository.findAll()).thenReturn(songEntities);

        // When
        List<TrackDTO> result = trackService.getAllTracks();

        // Then
        assertEquals(3, result.size());
        for (int i = 0; i < result.size(); i++) {
            TrackDTO trackDTO = result.get(i);
            TrackEntity trackEntity = songEntities.get(i);

            assertEquals(trackEntity.getId(), trackDTO.getId());
            assertEquals(trackEntity.getTitle(), trackDTO.getTitle());
            assertEquals(trackEntity.getDuration(), trackDTO.getDuration());
        }
    }

    @Test
    void test_valid_song_title() {
        // Given
        String title = "Valid Song Title";
        TrackEntity trackEntity = provideTrackEntity();
        trackEntity.setTitle(title);
        TrackDTO expectedTrackDTO = trackMapper.mapTrackEntityToTrackDTO(trackEntity);
        when(trackRepository.findByTitle(title)).thenReturn(Optional.of(trackEntity));

        // When
        TrackDTO result = trackService.getTrackByTitle(title);

        // Then
        assertEquals(expectedTrackDTO, result);
    }

    // Should throw ObjectNotFound when given a non-existent song title
    @Test
    void test_nonexistent_song_title() {
        // Given
        String title = "Nonexistent Song Title";
        when(trackRepository.findByTitle(title)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(ObjectNotFound.class, () -> trackService.getTrackByTitle(title));
    }

//    @Test
//    void test_addSong_validSongDTO() {
//        // Given
//        TrackDTO trackDTO = provideRandomTrack();
//        when(trackRepository.findByTitleAndArtist_Name(trackDTO.getTitle(), trackDTO.getArtist().getName())).thenReturn(Optional.empty());
//
//        // When
//        String result = trackService.addTrack(trackDTO);
//
//        // Then
//        assertEquals("Song added", result);
//        verify(trackRepository).save(any(TrackEntity.class));
//    }

//    @Test
//    void test_addSong_existingSong() {
//        // Given
//        TrackDTO trackDTO = provideRandomTrack();
//        TrackEntity trackEntity = trackMapper.mapTrackDTOtoTrackEntity(trackDTO);
//        when(trackRepository.findByTitleAndArtist_Name(trackDTO.getTitle(), trackDTO.getArtist().getName())).thenReturn(Optional.of(trackEntity));
//
//        // When/Then
//        assertThrows(EntityExistsException.class, () -> trackService.addTrack(trackDTO));
//        verify(trackRepository, times(0)).save(trackEntity);
//    }

//    @Test
//    void test_addSong_songAlreadyExists() {
//        // Given
//        TrackDTO trackDTO = provideRandomTrack();
//        TrackEntity existingTrackEntity = trackMapper.mapTrackDTOtoTrackEntity(trackDTO);
//        Optional<TrackEntity> existingSong = Optional.of(existingTrackEntity);
//        when(trackRepository.findByTitleAndArtist_Name(trackDTO.getTitle(), trackDTO.getArtist().getName())).thenReturn(existingSong);
//
//        // When
//        Throwable exception = assertThrows(EntityExistsException.class, () -> trackService.addTrack(trackDTO));
//
//        // Then
//        assertTrue(exception.getMessage().contains("Song already in database"));
//        verify(trackRepository, never()).save(any(TrackEntity.class));
//    }
}

package com.soundstock.mappers;

import com.soundstock.mapper.TrackMapper;
import com.soundstock.mapper.TrackMapperImpl;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.TrackEntity;
import com.soundstock.testdata.ResourceFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrackMapperTests extends ResourceFactory {

    TrackMapper trackMapper = new TrackMapperImpl();

    // mapSongEntityToDTOList should return a list of SongDTOs when given a list of SongEntities
//    @Test
//    public void test_mapSongEntityToDTOList_withListOfSongEntities() {
//        // Given
//        List<TrackEntity> trackList = provideTrackEntityList(2);
//
//        // When
//        List<TrackDTO> trackDTOS = trackMapper.mapTrackEntityToDTOList(trackList);
//
//        // Then
//        assertNotNull(trackDTOS);
//        assertEquals(2, trackDTOS.size());
//        assertEquals(trackList.get(0).getTitle(), trackDTOS.get(0).getTitle());
//        assertEquals(trackList.get(1).getTitle(), trackDTOS.get(1).getTitle());
//    }

    @Test
    public void test_mapTrackDTOtoTrackEntity_withTrackDTO() {
        // Given
        TrackDTO trackDTO = provideRandomTrackDTO();

        // When
        TrackEntity trackEntity = trackMapper.toEntity(trackDTO);

        // Then
        assertNotNull(trackEntity);
        assertEquals(trackDTO.getTitle(), trackEntity.getTitle());
        assertEquals(trackDTO.getDuration(), trackEntity.getDuration());
    }

    // mapSongEntityToSongDTO should return a SongDTO when given a SongEntity
//    @Test
//    public void test_mapSongEntityToSongDTO_withSongEntity() {
//        // Given
//        TrackEntity trackEntity = new TrackEntity(1L, "Song 1", new ArtistEntity(), 180, new AlbumEntity(), 1000L);
//
//        // When
//        TrackDTO trackDTO = trackMapper.mapTrackEntityToSongDTO(trackEntity);
//
//        // Then
//        assertNotNull(trackDTO);
//        assertEquals(1L, trackDTO.getId());
//        assertEquals("Song 1", trackDTO.getTitle());
//        assertNotNull(trackDTO.getArtist());
//        assertEquals(180, trackDTO.getDuration());
//        assertNotNull(trackDTO.getAlbum());
//    }

    // mapSongEntityToDTOList should return an empty list when given an empty list of SongEntities
    @Test
    public void test_mapSongEntityToDTOList_withEmptyListOfSongEntities() {
        // Given
        List<TrackEntity> songEntities = new ArrayList<>();

        // When
        List<TrackDTO> trackDTOS = trackMapper.toDTOList(songEntities);

        // Then
        assertNotNull(trackDTOS);
        assertTrue(trackDTOS.isEmpty());
    }
}

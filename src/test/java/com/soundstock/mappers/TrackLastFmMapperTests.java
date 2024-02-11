package com.soundstock.mappers;

import com.soundstock.mapper.TrackMapper;
import com.soundstock.mapper.TrackMapperImpl;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.TrackEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TrackLastFmMapperTests {

    TrackMapper trackMapper = new TrackMapperImpl();

    // mapSongEntityToDTOList should return a list of SongDTOs when given a list of SongEntities
    @Test
    public void test_mapSongEntityToDTOList_withListOfSongEntities() {
        // Given
        List<TrackEntity> songEntities = new ArrayList<>();
        songEntities.add(new TrackEntity(1L, "Song 1", new ArtistEntity(), 180, new AlbumEntity(), 10000L));
        songEntities.add(new TrackEntity(2L, "Song 2", new ArtistEntity(), 200, new AlbumEntity(), 10002L));

        // When
        List<TrackDTO> trackDTOS = trackMapper.mapTrackEntityToDTOList(songEntities);

        // Then
        assertNotNull(trackDTOS);
        assertEquals(2, trackDTOS.size());
        assertEquals("Song 1", trackDTOS.get(0).getTitle());
        assertEquals("Song 2", trackDTOS.get(1).getTitle());
    }

    // mapSongEntityToSongDTO should return a SongDTO when given a SongEntity
    @Test
    public void test_mapSongEntityToSongDTO_withSongEntity() {
        // Given
        TrackEntity trackEntity = new TrackEntity(1L, "Song 1", new ArtistEntity(), 180, new AlbumEntity(), 1000L);

        // When
        TrackDTO trackDTO = trackMapper.mapTrackEntityToSongDTO(trackEntity);

        // Then
        assertNotNull(trackDTO);
        assertEquals(1L, trackDTO.getId());
        assertEquals("Song 1", trackDTO.getTitle());
        assertNotNull(trackDTO.getArtist());
        assertEquals(180, trackDTO.getDuration());
        assertNotNull(trackDTO.getAlbum());
    }

    // mapSongEntityToDTOList should return an empty list when given an empty list of SongEntities
    @Test
    public void test_mapSongEntityToDTOList_withEmptyListOfSongEntities() {
        // Given
        List<TrackEntity> songEntities = new ArrayList<>();

        // When
        List<TrackDTO> trackDTOS = trackMapper.mapTrackEntityToDTOList(songEntities);

        // Then
        assertNotNull(trackDTOS);
        assertTrue(trackDTOS.isEmpty());
    }
}

package com.soundstock.mappers;

import com.soundstock.mapper.SongMapper;
import com.soundstock.mapper.SongMapperImpl;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.AlbumEntity;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.SongEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SongMapperTests {

    SongMapper songMapper = new SongMapperImpl();

    // mapSongEntityToDTOList should return a list of SongDTOs when given a list of SongEntities
    @Test
    public void test_mapSongEntityToDTOList_withListOfSongEntities() {
        // Given
        List<SongEntity> songEntities = new ArrayList<>();
        songEntities.add(new SongEntity(1L, "Song 1", new ArtistEntity(), 180, new AlbumEntity(), 10000L));
        songEntities.add(new SongEntity(2L, "Song 2", new ArtistEntity(), 200, new AlbumEntity(), 10002L));

        // When
        List<SongDTO> songDTOs = songMapper.mapSongEntityToDTOList(songEntities);

        // Then
        assertNotNull(songDTOs);
        assertEquals(2, songDTOs.size());
        assertEquals("Song 1", songDTOs.get(0).getTitle());
        assertEquals("Song 2", songDTOs.get(1).getTitle());
    }

    // mapSongEntityToSongDTO should return a SongDTO when given a SongEntity
    @Test
    public void test_mapSongEntityToSongDTO_withSongEntity() {
        // Given
        SongEntity songEntity = new SongEntity(1L, "Song 1", new ArtistEntity(), 180, new AlbumEntity(), 1000L);

        // When
        SongDTO songDTO = songMapper.mapSongEntityToSongDTO(songEntity);

        // Then
        assertNotNull(songDTO);
        assertEquals(1L, songDTO.getId());
        assertEquals("Song 1", songDTO.getTitle());
        assertNotNull(songDTO.getArtist());
        assertEquals(180, songDTO.getDuration());
        assertNotNull(songDTO.getAlbum());
    }

    // mapSongEntityToDTOList should return an empty list when given an empty list of SongEntities
    @Test
    public void test_mapSongEntityToDTOList_withEmptyListOfSongEntities() {
        // Given
        List<SongEntity> songEntities = new ArrayList<>();

        // When
        List<SongDTO> songDTOs = songMapper.mapSongEntityToDTOList(songEntities);

        // Then
        assertNotNull(songDTOs);
        assertTrue(songDTOs.isEmpty());
    }
}

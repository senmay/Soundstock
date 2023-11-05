package com.soundstock.mapper;

import com.soundstock.model.Song;
import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.ArtistEntity;
import com.soundstock.model.entity.SongEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SongMapper {
    Song mapToSong(SongDTO songDTO);
    SongDTO mapToSongDTO(Song song);
    List<SongDTO> mapToSongDTOList(List<Song> songs);
    Song mapToSong(SongEntity songEntity);
    SongEntity mapToSongEntity(Song song);
    List<SongEntity> mapToSongEntityList(List<Song> songs);
    List<Song> mapToSongList(List<SongEntity> songsEntity);
    List<SongDTO> mapSongEntityToDTOList(List<SongEntity> songEntities);
    SongDTO mapSongEntityToSongDTO(SongEntity songEntity);
    SongEntity mapSongDTOtoSongEntity(SongDTO songDTO);
    @Mapping(target="artist", source = "artistEntity")
    @Mapping(target="id", ignore = true)
    SongEntity mapSongDTOtoSongEntity(SongDTO songDTO, ArtistEntity artistEntity);
    List<SongEntity> mapSongDTOtoSongEntityList(List<SongDTO> songDTOS);


}

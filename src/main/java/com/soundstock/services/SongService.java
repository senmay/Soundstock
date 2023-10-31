package com.soundstock.services;

import com.soundstock.model.dto.SongDTO;
import com.soundstock.model.entity.SongEntity;
import com.soundstock.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;
    public List<SongEntity> getAllSongs() {
        return songRepository.findAll();
    }
    public SongEntity getSongById(Long id) {
        //TODO
        return songRepository.findById(id).get();
    }
    public SongEntity saveSong(SongDTO song) {
        //TODO
        return null;
    }
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
    public void deleteAllSongs() {
        songRepository.deleteAll();
    }



}

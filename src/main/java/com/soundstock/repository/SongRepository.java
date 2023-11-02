package com.soundstock.repository;

import com.soundstock.model.entity.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {
    Optional<SongEntity> findByTitleAndArtist_Name(String title, String artistName);
    Optional<SongEntity> findByTitle(String title);
}

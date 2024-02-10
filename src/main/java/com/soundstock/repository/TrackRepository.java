package com.soundstock.repository;

import com.soundstock.model.entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackRepository extends JpaRepository<TrackEntity, Long> {
    Optional<TrackEntity> findByTitle(String title);
}

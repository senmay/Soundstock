package com.soundstock.repository;

import com.soundstock.model.entity.PlaylistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistEntity, Long> {
    boolean existsByName(String name);
    Optional<PlaylistEntity> findByName(String name);
}

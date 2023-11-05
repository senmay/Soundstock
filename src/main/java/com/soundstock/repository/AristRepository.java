package com.soundstock.repository;

import com.soundstock.model.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AristRepository extends JpaRepository<ArtistEntity, Long> {
    Optional<ArtistEntity> findByName(String name);
}

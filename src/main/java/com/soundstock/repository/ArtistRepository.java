package com.soundstock.repository;

import com.soundstock.model.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ArtistRepository extends JpaRepository<ArtistEntity,Long> {
    Optional<ArtistEntity> findByName(String name);
}

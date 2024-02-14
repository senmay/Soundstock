package com.soundstock.repository;

import com.soundstock.model.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, Long> {
    Optional<AlbumEntity> findByTitle(String name);

}

package com.soundstock.repository;

import com.soundstock.model.entity.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<ArtistEntity,Long> {
    @Query("select (count(a) > 0) from ArtistEntity a where upper(a.name) = upper(?1)")
    boolean existsByNameIgnoreCase(String name);
    Optional<ArtistEntity> findByName(String name);
}

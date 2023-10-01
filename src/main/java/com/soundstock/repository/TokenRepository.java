package com.soundstock.repository;

import com.soundstock.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByValue(String value);
}


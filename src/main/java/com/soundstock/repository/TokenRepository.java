package com.soundstock.repository;

import com.soundstock.enums.TokenType;
import com.soundstock.model.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    Optional<TokenEntity> findByValue(String value);
    List<TokenEntity> findByUserEmailAndTypeAndUsed(String email, TokenType tokenType, boolean used);
    List<TokenEntity> findByUserEmailAndUsed(String email, boolean used);
}


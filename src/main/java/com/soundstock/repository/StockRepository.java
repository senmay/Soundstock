package com.soundstock.repository;

import com.soundstock.model.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    boolean existsByName(String name);
    StockEntity findByName(String name);

}

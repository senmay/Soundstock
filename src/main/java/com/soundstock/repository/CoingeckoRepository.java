package com.soundstock.repository;

import com.soundstock.model.entity.CoingeckoStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoingeckoRepository extends JpaRepository<CoingeckoStockEntity, Long> {
}

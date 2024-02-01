package com.soundstock.repository;

import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.model.entity.PortfolioItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItemEntity, Long> {

}

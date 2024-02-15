package com.soundstock.repository;

import com.soundstock.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserId(Long userId);
    @Query("SELECT s.name as stockName, SUM(o.quantity) as quantity, SUM(o.quantity * o.pricePerShare) as totalValue " +
            "FROM OrderEntity o JOIN o.stock s WHERE o.user.id = :userId GROUP BY s.name")
    List<PortfolioItemProjection> findPortfolioItemsByUserId(@Param("userId") Long userId);
}


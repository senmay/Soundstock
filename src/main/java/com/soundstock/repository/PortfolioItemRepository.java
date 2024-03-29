package com.soundstock.repository;

import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.model.entity.OrderEntity;
import com.soundstock.model.entity.PortfolioItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioItemRepository extends JpaRepository<PortfolioItemEntity, Long> {
    @Query("SELECT new com.soundstock.model.dto.PortfolioItemDTO(o.stock.name, SUM(o.quantity), SUM(o.transactionValue)) " +
            "FROM OrderEntity o WHERE o.transactionType = 'BUY' GROUP BY o.stock.name")
    List<PortfolioItemDTO> findTotalQuantitiesAndValuesForBuyTransactions(List<OrderEntity> orders);

    @Query("SELECT new com.soundstock.model.dto.PortfolioItemDTO(o.stock.name, SUM(o.quantity), SUM(o.transactionValue)) " +
            "FROM OrderEntity o WHERE o.transactionType = 'SELL' GROUP BY o.stock.name")
    List<PortfolioItemDTO> findTotalQuantitiesAndValuesForSellTransactions(List<OrderEntity> orders);

    @Query("SELECT new com.soundstock.model.dto.PortfolioItemDTO(o.stock.name, " +
            "SUM(CASE WHEN o.transactionType = 'BUY' then o.quantity else -o.quantity end), " +
            "SUM(CASE WHEN o.transactionType = 'BUY' then o.transactionValue else -o.transactionValue end)) " +
            "FROM OrderEntity o GROUP BY o.stock.name")
    List<PortfolioItemDTO> findTotalQuantitiesAndValuesForAllTransactions(List<OrderEntity> orders);
}

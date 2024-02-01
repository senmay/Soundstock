package com.soundstock.model.dto;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PortfolioItemDTO {
    Long id;
    String stockName;
    Integer quantity;
    BigDecimal totalValue;
    BigDecimal profitLoss;
    BigDecimal percentageChange;
}

package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PortfolioItemDTO {
    Long id;
    String stockName;
    Integer quantity;
    BigDecimal totalValue;
    BigDecimal profitLoss;
    BigDecimal percentageChange;
}

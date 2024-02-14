package com.soundstock.model.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PortfolioItemDTO {
    String stockName;
    Long quantity;
    BigDecimal totalValue;
}

package com.soundstock.model.dto;

import com.soundstock.enums.OrderType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PortfolioItemDTO {
    Long id;
    String assetName;
    Long quantity;
    BigDecimal totalValue;
    OrderType orderType;
}

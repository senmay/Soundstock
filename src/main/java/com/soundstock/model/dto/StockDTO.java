package com.soundstock.model.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class StockDTO {
    Long id;
    String name;
    String shortcut;
    BigDecimal price;
    Integer stockQuantity;
    BigDecimal percentageChange;
    List<OrderDTO> purchaseOrderList;
    List<OrderDTO> sellOrderList;
}

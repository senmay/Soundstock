package com.soundstock.model.dto;

import com.soundstock.enums.TransactionType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrderDTO {
    Long id;
    UserDTO user;
    TransactionType transactionType;
    StockDTO stock;
    Integer quantity;
    BigDecimal pricePerShare;
    BigDecimal transactionValue;
}

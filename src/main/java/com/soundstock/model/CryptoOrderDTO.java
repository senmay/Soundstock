package com.soundstock.model;

import com.soundstock.enums.OrderType;
import com.soundstock.enums.TransactionType;
import com.soundstock.model.dto.StockDTO;
import com.soundstock.model.dto.UserDTO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.math.BigDecimal;


@Value
@EqualsAndHashCode(callSuper = true)
public class CryptoOrderDTO extends BaseOrder{
    StockDTO stock;
    Integer quantity;
    BigDecimal pricePerShare;
    @Builder
    public CryptoOrderDTO(Long id, UserDTO user, TransactionType transactionType, BigDecimal transactionValue, OrderType orderType, StockDTO stock, Integer quantity, BigDecimal pricePerShare) {
        super(id, user, transactionType, transactionValue, orderType);
        this.stock = stock;
        this.quantity = quantity;
        this.pricePerShare = pricePerShare;
    }
}

package com.soundstock.model;

import java.math.BigDecimal;

public class CryptoOrderCostStrategy implements OrderCostStrategy{
    @Override
    public BigDecimal calculateCost(BaseOrder order) {
        CryptoOrderDTO cryptoOrder = (CryptoOrderDTO) order;
        return cryptoOrder.getPricePerShare().multiply(new BigDecimal(cryptoOrder.getQuantity()));
    }
}

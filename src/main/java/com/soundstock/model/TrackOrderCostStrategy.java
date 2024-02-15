package com.soundstock.model;

import java.math.BigDecimal;

public class TrackOrderCostStrategy implements OrderCostStrategy{
    @Override
    public BigDecimal calculateCost(BaseOrder order) {
        TrackOrderDTO trackOrder = (TrackOrderDTO) order;
        return trackOrder.getTransactionValue();
    }
}
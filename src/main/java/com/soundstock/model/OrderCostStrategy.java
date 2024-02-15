package com.soundstock.model;

import java.math.BigDecimal;

public interface OrderCostStrategy {
    BigDecimal calculateCost(BaseOrder order);
}

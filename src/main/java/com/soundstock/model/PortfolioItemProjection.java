package com.soundstock.model;

import java.math.BigDecimal;

public interface PortfolioItemProjection {
    String getName();

    Integer getQuantity();

    BigDecimal getValue();
}

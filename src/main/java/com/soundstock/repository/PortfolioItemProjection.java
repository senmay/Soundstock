package com.soundstock.repository;

import java.math.BigDecimal;

public interface PortfolioItemProjection {
    String getName();

    Integer getQuantity();

    BigDecimal getValue();
}

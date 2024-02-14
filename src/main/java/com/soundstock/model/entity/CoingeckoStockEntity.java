package com.soundstock.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coins")
public class CoingeckoStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    String symbol;
    BigDecimal price;
    String price1h;
    String price24h;
    String price7d;
    String price30d;
    BigDecimal totalVolume24h;
    BigDecimal circulatingSupply;
    String totalSupply;
    BigDecimal marketCap;

}

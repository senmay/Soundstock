package com.soundstock.model.entity;

import com.soundstock.enums.OrderType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="portfolioitems")
public class PortfolioItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String assetName;
    Long quantity;
    BigDecimal totalValue;
    @Enumerated(EnumType.STRING)
    OrderType orderType;
}

package com.soundstock.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stocks")
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String shortcut;
    BigDecimal price;
    Integer stockQuantity;
    BigDecimal percentageChange;
    @OneToMany
    @JoinColumn(name = "stock")
    List<OrderEntity> purchaseOrderList;
    @OneToMany
    @JoinColumn(name = "stock")
    List<OrderEntity> sellOrderList;

}

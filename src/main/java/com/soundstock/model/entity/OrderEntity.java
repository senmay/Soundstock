package com.soundstock.model.entity;

import com.soundstock.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")

public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    StockEntity stock;
    Integer quantity;
    BigDecimal pricePerShare;
    BigDecimal transactionValue;
    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;
}

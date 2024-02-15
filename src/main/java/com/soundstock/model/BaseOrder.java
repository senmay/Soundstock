package com.soundstock.model;

import com.soundstock.enums.OrderType;
import com.soundstock.enums.TransactionType;
import com.soundstock.model.dto.UserDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
public abstract class BaseOrder {
    Long id;
    UserDTO user;
    TransactionType transactionType;
    BigDecimal transactionValue;
    OrderType orderType;
}

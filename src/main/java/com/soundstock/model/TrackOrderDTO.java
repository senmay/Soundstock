package com.soundstock.model;

import com.soundstock.enums.OrderType;
import com.soundstock.enums.TransactionType;
import com.soundstock.model.dto.TrackDTO;
import com.soundstock.model.dto.UserDTO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;
import java.math.BigDecimal;
@Value
@EqualsAndHashCode(callSuper = true)
@Getter

public class TrackOrderDTO extends BaseOrder{
    TrackDTO track;
    @Builder
    public TrackOrderDTO(Long id, UserDTO user, TransactionType transactionType, BigDecimal transactionValue, OrderType orderType, TrackDTO track) {
        super(id, user, transactionType, transactionValue, orderType);
        this.track = track;
    }
}

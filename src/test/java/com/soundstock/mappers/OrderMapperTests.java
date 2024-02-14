package com.soundstock.mappers;

import com.soundstock.enums.TransactionType;
import com.soundstock.mapper.OrderMapper;
import com.soundstock.mapper.OrderMapperImpl;
import com.soundstock.model.dto.OrderDTO;
import com.soundstock.model.entity.OrderEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class OrderMapperTests {
    OrderMapper orderMapper = new OrderMapperImpl();
    @Test
    public void shouldMapOrderEntityToOrderDTO() {
        // Przygotowanie
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setTransactionType(TransactionType.BUY);
        orderEntity.setQuantity(10);
        orderEntity.setPricePerShare(new BigDecimal("100.00"));
        orderEntity.setTransactionValue(new BigDecimal("1000.00"));

        // Akcja
        OrderDTO orderDTO = orderMapper.mapOrderEntityToDto(orderEntity);

        // Weryfikacja
        assertEquals(orderDTO.getId(), orderEntity.getId());
        assertEquals(orderDTO.getTransactionType(), orderEntity.getTransactionType());
        assertEquals(orderDTO.getQuantity(), orderEntity.getQuantity());
        assertEquals(orderDTO.getPricePerShare(), orderEntity.getPricePerShare());
        assertEquals(orderDTO.getTransactionValue(), orderEntity.getTransactionValue());
    }
}

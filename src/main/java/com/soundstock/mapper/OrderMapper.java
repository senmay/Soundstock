package com.soundstock.mapper;

import com.soundstock.model.BaseOrder;
import com.soundstock.model.dto.OrderDTO;
import com.soundstock.model.entity.OrderEntity;
import com.soundstock.model.entity.UserEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrderMapper {
    @Mapping(target = "user", ignore = true)
    List<OrderDTO> mapOrderEntitesToDTOList(List<OrderEntity> orderEntities);
    @Mapping(target = "user", ignore = true)
    OrderDTO mapOrderEntityToDto(OrderEntity orderEntity);
    @Mapping(target = "transactionValue", source = "orderCost")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderEntity mapDTOToOrderEntity(BaseOrder baseOrder, BigDecimal orderCost);
    List<OrderEntity> mapOrderDTOToEntities(List<OrderDTO> orderDTOS);

}

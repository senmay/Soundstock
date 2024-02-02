package com.soundstock.mapper;

import com.soundstock.model.dto.OrderDTO;
import com.soundstock.model.entity.OrderEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Mapping(target = "user", ignore = true)
    List<OrderDTO> mapOrderEntitesToDTOList(List<OrderEntity> orderEntities);
    @Mapping(target = "user", ignore = true)
    OrderDTO mapOrderEntityToDto(OrderEntity orderEntity);
    OrderEntity mapDTOToOrderEntity(OrderDTO orderDTO);
    List<OrderEntity> mapOrderDTOToEntities(List<OrderDTO> orderDTOS);

}

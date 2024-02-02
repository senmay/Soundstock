package com.soundstock.mapper;

import com.soundstock.model.dto.OrderDTO;
import com.soundstock.model.entity.OrderEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderDTO> mapOrderEntitesToDTOList(List<OrderEntity> orderEntities);
    OrderDTO mapOrderEntityToDto(OrderEntity orderEntity);
    OrderEntity mapDTOToOrderEntity(OrderDTO orderDTO);
}

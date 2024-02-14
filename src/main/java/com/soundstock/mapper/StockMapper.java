package com.soundstock.mapper;

import com.soundstock.model.dto.StockDTO;
import com.soundstock.model.entity.StockEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StockMapper {
    List<StockDTO> toDTO(List<StockEntity> stockEntities);
    StockDTO toDTO(StockEntity stockEntity);
    StockEntity toEntity(StockDTO stockDTO);
}

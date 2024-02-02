package com.soundstock.mapper;

import com.soundstock.model.dto.StockDTO;
import com.soundstock.model.entity.StockEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StockMapper {
    List<StockDTO> mapStockEntitiesToDTOList(List<StockEntity> stockEntities);
    StockDTO mapStockEntityToDTO(StockEntity stockEntity);
    StockEntity mapDTOToAStockEntity(StockDTO stockDTO);
}

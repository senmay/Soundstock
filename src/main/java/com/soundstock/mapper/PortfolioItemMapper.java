package com.soundstock.mapper;

import com.soundstock.model.dto.PortfolioItemDTO;
import com.soundstock.model.entity.PortfolioItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PortfolioItemMapper {
    List<PortfolioItemEntity> mapPortfolioDTOListToEntityList(List<PortfolioItemDTO> list);
    List<PortfolioItemDTO> mapPortfolioEntityListToDTOList(List<PortfolioItemEntity> list);
    PortfolioItemDTO mapToDTO(PortfolioItemEntity item);
    PortfolioItemEntity mapToEntity(PortfolioItemDTO item);
}

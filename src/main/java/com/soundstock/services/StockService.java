package com.soundstock.services;

import com.soundstock.exceptions.ObjectNotFound;
import com.soundstock.mapper.StockMapper;
import com.soundstock.model.dto.StockDTO;
import com.soundstock.model.entity.StockEntity;
import com.soundstock.repository.StockRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.soundstock.exceptions.ErrorMessages.USERNAME_OR_EMAIL_EXISTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    public String createStock(StockDTO stockDTO) {
        if (stockRepository.existsByName(stockDTO.getName())){
            throw new EntityExistsException(USERNAME_OR_EMAIL_EXISTS);
        }
        StockEntity stockEntity = stockMapper.mapDTOToAStockEntity(stockDTO);
        stockRepository.save(stockEntity);
        return "Stock created successfully";
    }

    public List<StockDTO> getAllStocks() {
        return stockMapper.mapStockEntitiesToDTOList(stockRepository.findAll());
    }

    public String deleteStock(StockDTO stockDTO) {
        if (!stockRepository.existsByName(stockDTO.getName())){
            throw new ObjectNotFound("Stock doesn't exist");
        }
        stockRepository.deleteById(stockDTO.getId());
        return "Stock deleted successfully";
    }

    public StockDTO getStock(Long id) {
        if (!stockRepository.existsById((id))){
            throw new ObjectNotFound("Stock doesn't exist");
        }
        return stockMapper.mapStockEntityToDTO(stockRepository.findById(id).get());
    }
}

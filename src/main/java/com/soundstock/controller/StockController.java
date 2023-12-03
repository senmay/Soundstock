package com.soundstock.controller;

import com.soundstock.model.dto.StockDTO;
import com.soundstock.services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("stock/v1")
public class StockController {
    private final StockService stockService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addStock(@RequestBody StockDTO stockDTO){
        return stockService.createStock(stockDTO);
    }
    @GetMapping("/stocklist")
    @ResponseStatus(HttpStatus.OK)
    public List<StockDTO> getAllStocks(){
        return stockService.getAllStocks();
    }
    @DeleteMapping("delete")
    public String deleteStock(@RequestBody StockDTO stockDTO){
        return stockService.deleteStock(stockDTO);
    }
    @GetMapping("/id")
    public StockDTO getStock(@PathVariable Long id){
        return stockService.getStock(id);
    }
}

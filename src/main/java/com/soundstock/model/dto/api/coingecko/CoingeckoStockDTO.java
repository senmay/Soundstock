package com.soundstock.model.dto.api.coingecko;

import lombok.Data;

import java.math.BigDecimal;

//todo merge with StockDTO
@Data
public class CoingeckoStockDTO {
    String name;
    String symbol;
    BigDecimal price;
    String price1h;
    String price24h;
    String price7d;
    String price30d;
    BigDecimal totalVolume24h;
    BigDecimal circulatingSupply;
    String totalSupply;
    BigDecimal marketCap;


}

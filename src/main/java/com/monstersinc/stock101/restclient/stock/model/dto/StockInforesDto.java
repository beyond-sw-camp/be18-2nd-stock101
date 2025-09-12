package com.monstersinc.stock101.restclient.stock.model.dto;

import lombok.Data;

@Data
public class StockInfoResDto {
    private String stockCode;
    private String stockName;
    private Double price;
}
package com.monstersinc.stock101.restclient.stock.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Data
@Getter
@ToString
public class StockInforesDto {
    private String stockCode;
    private String stockName;
    private Double price;
}
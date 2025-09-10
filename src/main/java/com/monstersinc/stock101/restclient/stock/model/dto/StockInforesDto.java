package com.monstersinc.stock101.restclient.stock.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class StockInforesDto {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record StockInfo(String stockName, String stockPrice, String stockChange, String stockChangeRate, String stockVolume) {
    }

}

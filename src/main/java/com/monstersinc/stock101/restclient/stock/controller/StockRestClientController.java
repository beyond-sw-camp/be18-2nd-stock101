package com.monstersinc.stock101.restclient.stock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monstersinc.stock101.restclient.stock.model.dto.StockInfoResDto;
import com.monstersinc.stock101.restclient.stock.model.service.StockRestClientService;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class StockRestClientController {

    private final StockRestClientService stockRestClientService;

    @GetMapping("rest-client/getFinancialInfo/{stockCode}")
    public StockInfoResDto getStockInfo(@PathVariable String stockCode) {
        return stockRestClientService.getFinancialInfo(stockCode, "annual");
    }
}

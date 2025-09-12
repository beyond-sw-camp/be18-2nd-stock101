package com.monstersinc.stock101.restclient.stock.model.service;

import com.monstersinc.stock101.restclient.stock.model.dto.StockInfoResDto;

public interface StockRestClientService {
    StockInfoResDto getFinancialInfo(String ticker, String timeframe);
}

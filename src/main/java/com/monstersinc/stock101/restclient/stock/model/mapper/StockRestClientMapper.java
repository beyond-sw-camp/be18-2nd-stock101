package com.monstersinc.stock101.restclient.stock.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.monstersinc.stock101.restclient.stock.model.dto.StockInforesDto;

@Mapper
public interface StockRestClientMapper {
    @Insert("INSERT INTO stock_info (stock_code, stock_name, price) VALUES (#{stockCode}, #{stockName}, #{price})")
    void insertStockInfo(StockInforesDto stockInforesDto);
}
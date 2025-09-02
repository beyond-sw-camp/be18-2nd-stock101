package com.monstersinc.stock101.stock.model.service;

import com.monstersinc.stock101.stock.model.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    private final StockMapper stockMapper;
}

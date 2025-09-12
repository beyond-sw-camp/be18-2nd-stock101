package com.monstersinc.stock101.indicator.model.service;

import com.monstersinc.stock101.indicator.model.mapper.IndicatorMapper;
import com.monstersinc.stock101.indicator.model.vo.AnalystIndicator;
import com.monstersinc.stock101.indicator.model.vo.IndividualIndicator;
import com.monstersinc.stock101.indicator.model.vo.NewsIndicator;
import com.monstersinc.stock101.stock.model.mapper.StockMapper;
import com.monstersinc.stock101.stock.model.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndicatorServiceImpl implements IndicatorService {
    private final IndicatorMapper indicatorMapper;
    @Override
    public IndividualIndicator getIndividualIndicator(long stockId) {
        return indicatorMapper.selectIndividualIndicatorByStockId(stockId);
    }

    @Override
    public AnalystIndicator getAnalystIndicator(long stockId) {
        return indicatorMapper.selectAnalystIndicatorByStockId(stockId);
    }

    @Override
    public NewsIndicator getNewsIndicator(long stockId) {
        return indicatorMapper.selectNewsIndicatorByStockId(stockId);
    }


}

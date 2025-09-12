package com.monstersinc.stock101.restclient.stock.model.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monstersinc.stock101.restclient.stock.model.PolygonResponse;
import com.monstersinc.stock101.restclient.stock.model.dto.StockInfoResDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockRestClientServiceImpl implements StockRestClientService {

    //application.yml에서 가져오기
    @Value("${apikey.stock-key}")
    private String stockKey;

    private final ObjectMapper objectMapper;

    private StockInfoResDto stockInfoResDto;

    private final RestTemplate restTemplate;

    // @Override
    // public Object getStockInfo(String stockCode) {
    //     RestTemplateBuilder builder = new RestTemplateBuilder();
    //     RestTemplate restTemplate = builder.build();
    //     String url = "https://api.polygon.io/vX/last/stocks/" + stockCode + "?apikey=" + stockKey;


    // }
    //재무제표 가져오기

    // 진짜 머리뽑으면서 짠 코드 
    @Override
    public StockInfoResDto getFinancialInfo(String ticker, String timeframe) {
            try{
                String url = "https://api.polygon.io/vX/reference/financials"
                    + "?ticker=" + ticker
                    + "&timeframe=" + timeframe 
                    + "&limit=4"
                    + "&apikey=" + stockKey;

                String body = restTemplate.getForObject(url, String.class);
                PolygonResponse pr = objectMapper.readValue(body, PolygonResponse.class);

                if (pr.getResults() == null || pr.getResults().size() < 2) {
                    throw new IllegalArgumentException("재무제표가 충분하지 않습니다" + ticker);
                }

                var r0 = pr.getResults().get(0);
                var r1 = pr.getResults().get(1);

                var bs0 = r0.getFinancials().getBalanceSheet();
                var bs1 = r1.getFinancials().getBalanceSheet();
                var is0 = r0.getFinancials().getIncomeStatement();

                double eq0 = nz(bs0.getEquityAttributableToParent().getValue());
                double eq1 = nz(bs1.getEquityAttributableToParent().getValue());
                double assets0 = nz(bs0.getAssets().getValue());
                double assets1 = nz(bs1.getAssets().getValue());

                double netIncome = nz(is0.getNetIncomeLoss().getValue());
                double epsBasic  = nz(is0.getBasicEps().getValue());
                double shares    = nz(is0.getBasicAverageShares().getValue());

                double avgEq = (eq0 + eq1) / 2.0;
                double avgAssets = (assets0 + assets1) / 2.0;

                Double roe = (avgEq == 0) ? null : netIncome / avgEq;
                Double roa = (avgAssets == 0) ? null : netIncome / avgAssets;
                Double bps = (shares == 0) ? null : eq0 / shares;
                return StockInfoResDto.builder()
                    .ticker(ticker)
                    .timeframe(timeframe)
                    .periodEnd(r0.getEndDate())
                    .roe(roe)
                    .roa(roa)
                    .eps(Double.isFinite(epsBasic) ? epsBasic : null)
                    .bps(bps)
                    .build();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
    private double nz(Double val) {
        return (val == null) ? 0.0 : val;
    }   
}
package com.monstersinc.stock101.restclient.stock.model.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockRestClientServiceImpl implements StockRestClientService {

    //application.yml에서 가져오기
    @Value("${apikey.stock-key}")
    private String stockKey;
      
    //ObjectMapper
	private final ObjectMapper objectMapper;
    //주식정보 가져오기
    @Override
    public Object getStockInfo(String stockCode) {
            RestTemplateBuilder builder = new RestTemplateBuilder();
            RestTemplate restTemplate = builder.build();

            String url = "https://api.polygon.io/vX/reference/financials?ticker=" + stockCode + "order=asc&limit=10&sort=filing_date" +"&apikey=" + stockKey;
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
            Object body = response.getBody();
            return body;
        }
    }

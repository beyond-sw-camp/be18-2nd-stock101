package com.monstersinc.stock101.restclient.stock.model.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockRestClientServiceImpl implements StockRestClientService {

    @Override
    public String test(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
        Object obj = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Object.class);
    return obj.toString(); 
    }
}

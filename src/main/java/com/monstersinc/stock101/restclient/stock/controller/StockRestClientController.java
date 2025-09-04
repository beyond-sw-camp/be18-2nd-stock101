package com.monstersinc.stock101.restclient.stock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
public class StockRestClientController {
    @GetMapping("/api/v1/rest-client/")
    public Object restClient() {
        return null;
    }
    
}       

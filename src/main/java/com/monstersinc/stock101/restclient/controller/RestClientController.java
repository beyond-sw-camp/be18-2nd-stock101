package com.monstersinc.stock101.restclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monstersinc.stock101.restclient.model.service.RestClientService;



@RestController
public class RestClientController {
    
    private RestClientService restClientService;
    @GetMapping("/api/v1/rest-client/")
    public String restClient(@RequestParam String url) {
        return restClientService.test();
    }
    
}       

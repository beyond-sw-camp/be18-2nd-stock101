package com.monstersinc.stock101.restclient.model.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestClientServiceImpl implements RestClientService {

    @Override
    public String test(){
        RestClient restClient =  RestClient.create();
        String res = restClient.get().uri("").retrieve().body(String.class);
        return res;
    }
}

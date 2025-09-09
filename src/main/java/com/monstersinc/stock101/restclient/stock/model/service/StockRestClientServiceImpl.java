package com.monstersinc.stock101.restclient.stock.model.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monstersinc.stock101.restclient.stock.model.dto.TokenResDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockRestClientServiceImpl implements StockRestClientService {

    @Value("${apikey.stock-key}")
    private String stockKey;
    @Value("${apikey.stock-Secret}")
    private String stockSecret;
	private final ObjectMapper objectMapper;

    @Override
    public String test(){
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
        Object obj = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts", Object.class);
    return obj.toString(); 
    }

    @Override
    public void getOAuthToken() {
        try {
            RestTemplateBuilder builder = new RestTemplateBuilder();
            RestTemplate restTemplate = builder.build();
            
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("grant_type", "client_credentials");
            requestMap.put("appkey", stockKey);
            requestMap.put("appsecret", stockSecret);
            
            String jsonBody = objectMapper.writeValueAsString(requestMap);
            
            HttpEntity<String> requestMessage = new HttpEntity<>(jsonBody, httpHeaders);
            
            String URL = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";
            HttpEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, requestMessage, String.class);
            
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            
            TokenResDto.TokenRes tokenRes = objectMapper.readValue(response.getBody(), TokenResDto.TokenRes.class);
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            tokenRes.accessToken();
            tokenRes.accessTokenTokenExpired();
            tokenRes.TokenType();
            tokenRes.expiresIn();
            System.out.println("Access Token: " + tokenRes.accessToken());
            System.out.println("Token Expiry: " + tokenRes.accessTokenTokenExpired());
            System.out.println("Token Type: " + tokenRes.TokenType());
            System.out.println("Expires In: " + tokenRes.expiresIn() + " seconds");
            System.out.println("Token Retrieved At: " + LocalDateTime.now().format(formatter));
            System.out.println("Token Valid Until: " + LocalDateTime.now().plusSeconds(tokenRes.expiresIn()).format(formatter) );
        }
        catch (Exception e) {
                e.printStackTrace();    
    }
}
}

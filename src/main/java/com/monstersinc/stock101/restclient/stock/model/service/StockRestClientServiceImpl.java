package com.monstersinc.stock101.restclient.stock.model.service;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monstersinc.stock101.restclient.stock.model.dto.TokenResDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockRestClientServiceImpl implements StockRestClientService {

    //application.yml에서 가져오기
    @Value("${apikey.stock-key}")
    private String stockKey;
    @Value("${apikey.stock-Secret}")
    private String stockSecret;
      
    //ObjectMapper
	private final ObjectMapper objectMapper;

    //토큰 저장용
    private TokenResDto.TokenRes tokenRes;

    //Redis
    private final RedisTemplate<String, String> redisTemplate;

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
            
            //Map -> JSON
            String jsonBody = objectMapper.writeValueAsString(requestMap);
            
            HttpEntity<String> requestMessage = new HttpEntity<>(jsonBody, httpHeaders);
            
            String URL = "https://openapi.koreainvestment.com:9443/oauth2/tokenP";

            //POST방식으로 요청
            HttpEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, requestMessage, String.class);
            
            //JSON -> Object -> DTO
            objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            tokenRes = objectMapper.readValue(response.getBody(), TokenResDto.TokenRes.class);
            System.out.println(tokenRes.accessToken());

            redisTemplate.opsForValue().set("stockApiToken", tokenRes.accessToken());
            redisTemplate.opsForValue().set("stockApiTokenExpiry", tokenRes.accessTokenTokenExpired());
        }
        catch (Exception e) {
                e.printStackTrace();    
    }
}

    //token 유효성 검사
    private boolean tokenValidYn() {
        String stockApiToken = redisTemplate.opsForValue().get("stockApiToken");
        String stockApiTokenExpiry = redisTemplate.opsForValue().get("stockApiTokenExpiry");
        if (stockApiToken == null) {
            return false;
        }
        LocalDateTime tokenExpiryTime = LocalDateTime.parse(stockApiTokenExpiry, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return LocalDateTime.now().isBefore(tokenExpiryTime);
    }

    //주식정보 가져오기
    @Override
    public Object getStockInfo(String stockCode) {
        int retryCount = 0;
        while (retryCount < 2) {

        if(tokenValidYn()){
            RestTemplateBuilder builder = new RestTemplateBuilder();
            RestTemplate restTemplate = builder.build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("authorization", "Bearer "+tokenRes.accessToken());
            httpHeaders.set("appKey", stockKey);
            httpHeaders.set("appSecret", stockSecret);
            httpHeaders.set("tr_id", "CTPF1702R");

            // 헤더만 담은 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            // 쿼리 파라미터 구성
            @SuppressWarnings("deprecation")
            URI uri = UriComponentsBuilder
                    .fromHttpUrl("https://openapi.koreainvestment.com:9443/uapi/overseas-price/v1/quotations/search-info")
                    .queryParam("PRDT_TYPE_CD", "512")   // 나스닥
                    .queryParam("PDNO", stockCode)       // 종목코드
                    .build()
                    .encode()
                    .toUri();

            // 요청 실행
            ResponseEntity<Object> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );
            System.out.println(response.getBody());
            return response;
        } else {
            getOAuthToken();
            retryCount++;
            
        }
        }
    throw new RuntimeException("Failed to get valid token after retries");
    }
    //주식상세정보 가져오기
    @Override
    public Object getStockprice(String stockCode) {
        int retryCount = 0;
        while (retryCount < 2) {

        if(tokenValidYn()){
            RestTemplateBuilder builder = new RestTemplateBuilder();
            RestTemplate restTemplate = builder.build();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("authorization", "Bearer "+tokenRes.accessToken());
            httpHeaders.set("appKey", stockKey);
            httpHeaders.set("appSecret", stockSecret);
            httpHeaders.set("tr_id", "CTPF1702R");

            // 헤더만 담은 HttpEntity
            HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

            // 쿼리 파라미터 구성
            @SuppressWarnings("deprecation")
            URI uri = UriComponentsBuilder
                    .fromHttpUrl("ttps://openapi.koreainvestment.com:9443/uapi/overseas-price/v1/quotations/inquire-asking-price")
                    .queryParam("AUTH", " ")
                    .queryParam("EXCD", "NAS")   // 나스닥
                    .queryParam("SYMB", stockCode)       // 종목코드
                    .build()
                    .encode()
                    .toUri();

            // 요청 실행
            ResponseEntity<Object> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );
            System.out.println(response.getBody());
            return response;
        } else {
            getOAuthToken();
            retryCount++;
            
        }
        }
    throw new RuntimeException("Failed to get valid token after retries");
    }
}

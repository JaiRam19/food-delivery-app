package com.codewave.orderservice.client;

import com.codewave.orderservice.dto.OrderItem;
import com.codewave.orderservice.exception.APIException;
import com.codewave.orderservice.util.AuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Getter
@Setter
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductClient {

    private final RestTemplate restTemplate;
    private final AuthService authService;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public Double calculateTotal(List<OrderItem> request){
        String url = productServiceUrl+"/calculate-total";

        //create header to pass auth header to the user service as it is authenticating users
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getAuthHeader());

        //create entity to set headers
        HttpEntity<List<OrderItem>> httpEntity = new HttpEntity<>(request, headers);
        try{
            //return restTemplate.postForEntity(url, request, Double.class).getBody();
            return restTemplate.exchange(url, HttpMethod.POST, httpEntity, Double.class).getBody();
        }catch (Exception e){
            log.info("Exception occurred while calling product service {}",e.getMessage());
            throw  new APIException(e.getMessage());
        }
    }
}

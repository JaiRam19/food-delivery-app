package com.codewave.orderservice.client;

import com.codewave.orderservice.exception.APIException;
import com.codewave.orderservice.util.AuthService;
import lombok.AllArgsConstructor;
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

@Service
@RequiredArgsConstructor
@Setter
@Getter
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;
    private final AuthService authService;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public Boolean isValidUser(Long userId){
        String url = userServiceUrl+"/validate/{userId}";

        //create header to pass auth header to the user service as it is authenticating users
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getAuthHeader());
        log.info("Auth header: {}", authService.getAuthHeader());

        //create entity to set headers
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(headers);

        try{
            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    httpEntity,
                    Boolean.class,
                    userId).getBody();
        }catch (Exception e){
            throw new APIException(e.getMessage());
        }
    }
}

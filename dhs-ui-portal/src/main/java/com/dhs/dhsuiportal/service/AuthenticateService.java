package com.dhs.dhsuiportal.service;

import com.dhs.dhsuiportal.model.TokenKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class AuthenticateService {

    @Autowired
    private TokenCacheService tokenCacheService;

    public String authenticate(final String username, final String password) {
        RestTemplate restTemplate = new RestTemplate();
        String cvanalyticsUrl
                = "https://${server-name}/data-api/login";

        Map<String, String> map = new HashMap<>();
        map.put("user_name", username);
        map.put("user_password", password);

        ResponseEntity<TokenKey> responseEntity
                = restTemplate.postForEntity(cvanalyticsUrl, map, TokenKey.class);

        TokenKey token = responseEntity.getBody();

        tokenCacheService.add(token);

        return token.getToken();
    }
}

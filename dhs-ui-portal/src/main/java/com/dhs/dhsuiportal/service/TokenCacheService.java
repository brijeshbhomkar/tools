package com.dhs.dhsuiportal.service;

import com.dhs.dhsuiportal.model.TokenKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Service
public class TokenCacheService {

    private static Map<LocalDateTime, TokenKey> cacheMap = new LinkedHashMap();

    public void add(TokenKey key) {
        cacheMap.clear();
        cacheMap.putIfAbsent(LocalDateTime.now(), key);
    }

    public TokenKey get() {
        return cacheMap.entrySet().stream().findFirst().get().getValue();
    }

}

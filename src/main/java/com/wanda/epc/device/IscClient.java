package com.wanda.epc.device;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class IscClient {

    @Value("${isc.host}")
    private String host;

    @Value("${isc.appKey}")
    private String appKey;

    @Value("${isc.appSecret}")
    private String appSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    private String token;
    private long expireTime;

    private synchronized String getToken() {
        if (token != null && System.currentTimeMillis() < expireTime) {
            return token;
        }

        String url = host + "/api/system/v1/auth/token";
        Map<String, String> body = new HashMap<>();
        body.put("appKey", appKey);
        body.put("appSecret", appSecret);

        JSONObject resp = restTemplate.postForObject(url, body, JSONObject.class);
        JSONObject data = resp.getJSONObject("data");

        token = data.getString("accessToken");
        expireTime = System.currentTimeMillis() + data.getLong("expireTime") * 1000;
        return token;
    }

    public JSONObject post(String path, Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getToken());

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(host + path, entity, JSONObject.class);
    }
}
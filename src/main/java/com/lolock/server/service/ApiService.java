package com.lolock.server.service;

import com.lolock.server.model.TempUrl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ApiService {

    private RestTemplate restTemplate;
    private static String DEVICE_ID = "00000174d02544fffef0103d";
    private static String LTID = "f0103d";

    public ApiService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> checkPhone(String phoneId) {
        List<String> phones = new ArrayList<>();
        phones.add("dClroxTH7Tk:APA91bEXwacWZlFEno-sKEVD_yG_p7OauLYaME8mnVbupmWmpmAaUMap4sQrsMqxaDCDFa-DYVgUVzBwcHUuKL5VRFCM2Ao8jt0HREbqdGhY-0yrF6dGNxhEGss-89MjPAPY3fHpkREl");

        Map<String, Object> result = new HashMap<>();
        if (phones.contains(phoneId)) {
            result.put("code", "REGISTERED");
            result.put("message", "등록된 휴대폰");

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("name", "이종구");
            userInfo.put("lolockLTID", LTID);

            result.put("userInfo", userInfo);
        } else {
            result.put("code", "NOT REGISTERED");
            result.put("message", "미등록 휴대폰");
            result.put("userInfo", "");

            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("name", "");
            userInfo.put("lolockLTID", "");

            result.put("userInfo", userInfo);
        }

        return result;
    }

    public Map<String, String> checkDevice(String deviceId) {
        Map<String, String> result = new HashMap<>();
        if (deviceId.equals(LTID)) {
            result.put("code", "DEVICE_ID_AVAILABLE");
            result.put("message", "등록된 기기");
        } else {
            result.put("code", "DEVICE_ID_ERR");
            result.put("message", "등록되지 않은 기기");
        }

        return result;
    }

    public String sendCommand(String deviceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");
        headers.add("X-M2M-RI", deviceId + "_0012");
        headers.add("X-M2M-Origin", deviceId);
        headers.add("uKey", System.getenv("LOLOCK_UKEY"));
        headers.add("Content-Type", "application/xml");

        String url = "https://thingplugpf.sktiot.com:9443/0240771000000174/v1_0/mgmtCmd-" + deviceId + "_extDevMgmt";
        String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:mgc xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><exe>true</exe><exra>26</exra></m2m:mgc>";
        HttpEntity entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
    }

    @Cacheable(value = "tempUrlPathVariable", key = "#key")
    public String getTempPathVariableWithCache(int key, TempUrl tempUrl) {
        return tempUrl.getPathVariable();
    }

    @Cacheable(value = "key", key = "#pathVariable")
    public int getKeyWithCache(String pathVariable, TempUrl tempUrl) {
        return tempUrl.getKey();
    }

    public Map<String, Object> register(Map<String, Object> requestBody) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "SUCCESS");
        result.put("message", "등록 성공");
        return result;
    }
}

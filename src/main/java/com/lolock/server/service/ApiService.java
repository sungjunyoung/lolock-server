package com.lolock.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private RestTemplate restTemplate;

    public ApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String healthCheck() {
        return "pong";
    }

    public String sendCommand(String deviceId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/xml");
        headers.add("X-M2M-RI", deviceId + "_0012");
        headers.add("X-M2M-Origin", deviceId);
        headers.add("uKey", System.getenv("LOLOCK_UKEY"));
        headers.add("Content-Type", "application/xml");

        String url =  "https://thingplugpf.sktiot.com:9443/0240771000000174/v1_0/mgmtCmd-" + deviceId + "_extDevMgmt";
        String body = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><m2m:mgc xmlns:m2m=\"http://www.onem2m.org/xml/protocols\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><exe>true</exe><exra>26</exra></m2m:mgc>";
        HttpEntity entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
    }
}

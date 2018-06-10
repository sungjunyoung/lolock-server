package com.lolock.server.controller;

import com.lolock.server.model.TempUrl;
import com.lolock.server.service.ApiService;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ApiController {
    private static AtomicInteger COUNTER = new AtomicInteger(1);

    @Autowired
    private ApiService apiService;

    @GetMapping(value = "/ping", produces = "application/json; charset=utf8")
    public String healthCheck(HttpServletRequest httpServletRequest) {
        return "pong";
    }

    @GetMapping(value = "/phones/{phoneId}", produces = "application/json; charset=utf8")
    public Map<String, Object> checkPhone(@PathVariable String phoneId) {
        return apiService.checkPhone(phoneId);
    }

    /**
     * 기기 등록 정보 확인
     */
    @GetMapping(value = "/devices/{deviceId}", produces = "application/json; charset=utf8")
    public Map<String, String> checkDevice(@PathVariable String deviceId) {
        return apiService.checkDevice(deviceId);
    }

    /**
     * 문 열기
     */
    @GetMapping(value = "/open", produces = "application/json; charset=utf8")
    public String openDoor(HttpServletRequest httpServletRequest) {
        return apiService.sendCommand("00000174d02544fffef0103d");
    }

    /**
     * 임시 URL 발급
     */
    @GetMapping(value = "/temp-url", produces = "application/json; charset=utf8")
    public String getTempUrl(HttpServletRequest httpServletRequest) {
        String base = httpServletRequest.getLocalName();
        String baseUrl = base.equals("localhost") ? "localhost" : "http://13.209.29.184";
        String port = baseUrl.equals("localhost") ? "8080" : "3000";

        int key = COUNTER.getAndIncrement();
        // add cache
        TempUrl tempUrl = new TempUrl(key);
        String tempPathVariable = apiService.getTempPathVariableWithCache(key, tempUrl);
        apiService.getKeyWithCache(tempPathVariable, tempUrl);

        return baseUrl + ":" + port + "/" + tempPathVariable;
    }

    /**
     * 임시 URL 로 접근
     */
    @GetMapping(value = "/{tempPathVariable}", produces = "application/json; charset=utf8")
    public String tempOpen(@PathVariable String tempPathVariable) throws HttpResponseException {
        TempUrl tempUrl = new TempUrl();
        int key = apiService.getKeyWithCache(tempPathVariable, tempUrl);
        if (key == 0) throw new HttpResponseException(401, "NO!");

        return apiService.sendCommand("00000174d02544fffef0103d");
    }

    @PostMapping(value = "/users", produces = "application/json; charset=utf8")
    public Map<String, Object> register(@RequestBody Map<String, Object> requestBody) {
        return apiService.register(requestBody);
    }
}

package com.lolock.server.controller;

import com.lolock.server.model.TempUrl;
import com.lolock.server.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ApiController {
    private static AtomicInteger COUNTER = new AtomicInteger(0);

    @Autowired
    private ApiService apiService;

    @GetMapping(value = "/ping", produces = "application/json; charset=utf8")
    public String healthCheck(HttpServletRequest httpServletRequest) {
        return "pong";
    }

    @GetMapping(value = "/phones/{phoneId}", produces = "application/json; charset=utf8")
    public Map<String, Object> checkPhone(@PathVariable String phoneId){
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
        String baseUrl = httpServletRequest.getLocalName();
        String port = baseUrl.equals("localhost") ? "8080" : "3000";
        int key = 1;
//        int key = COUNTER.getAndIncrement();

        TempUrl tempUrl = new TempUrl(key);
        return baseUrl + ":" + port + "/" + apiService.getTempPathVariableWithCache(key, tempUrl);
    }

//    @GetMapping(value = "/{pathVariable}", produces = "application/json; charset=utf8")
//    public int tempOpen(@PathVariable String pathVariable) {
//        return apiService.tempOpen(pathVariable);
//    }
}

package com.lolock.server.controller;

import com.lolock.server.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;


    @GetMapping(value = "/ping", produces = "application/json; charset=utf8")
    public String healthCheck(HttpServletRequest httpServletRequest) {
        return "pong";
    }

    /**
     * 기기 등록 정보 확인
     */
    @GetMapping(value = "/devices/{deviceId}", produces = "application/json; charset=utf8")
    public Map<String, String> checkDevice(@PathVariable String deviceId) {
        return apiService.checkDevice(deviceId);

    }

    @GetMapping(value = "/open", produces = "application/json; charset=utf8")
    public String openDoor(HttpServletRequest httpServletRequest) {
        return apiService.sendCommand("00000174d02544fffef0103d");
    }
}

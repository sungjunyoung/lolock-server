package com.lolock.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class Controller {

    @GetMapping(value = "/ping", produces = "application/json; charset=utf8")
    public String healthCheck(HttpServletRequest httpServletRequest) {
        return "pong";
    }
}

package com.gochanghai.cloudweb.controller;


import com.gochanghai.cloudweb.feign.service.UserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserFeignService userFeignService;

    @PostMapping(value = "login")
    public String login(@RequestBody Map<String, Object> dto) {
        return userFeignService.test(dto);
    }

    @PostMapping(value = "logout")
    public String logout(String message) {
        return "Hello Nacos Discovery " + message;
    }
}

package com.gochanghai.dubboconsumer.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.gochanghai.dubboapi.service.HelloService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class HelloController {

    @Reference(version = "${demo.service.version}")
    private HelloService helloService;

    @PostMapping("sayHello")
    public String sayHello(@RequestBody Map<String, String> dto) {
        return helloService.sayHello(dto.get("desc"));
    }
}

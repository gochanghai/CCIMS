package com.gochanghai.dubboprovider.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gochanghai.dubboapi.service.HelloService;

@Service(version = "${demo.service.version}")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello , "+name;
    }
}

package com.gochanghai.rocketmq;

import com.gochanghai.rocketmq.service.RocketmqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketmqApplication implements CommandLineRunner {

    @Autowired
    private RocketmqService rocketmqService;

    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication.class, args);
    }

    @Override
    public void run(String... args) {
        int count = 5;
        for (int i = 0; i < count; i++) {
            rocketmqService.send("rocketmqServiceImpl");
        }

    }
}

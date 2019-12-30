package com.lch.ccims.redismq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CcimsEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcimsEurekaApplication.class, args);
    }

}

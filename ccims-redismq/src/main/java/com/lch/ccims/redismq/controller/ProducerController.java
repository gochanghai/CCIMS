package com.lch.ccims.redismq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@RequestMapping("/redis/producer")
@Controller
public class ProducerController {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 发布消息
     * @param id
     * @return
     */
    @RequestMapping("/sendMessage/{id}")
    public String sendMessage(@PathVariable String id) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        jedisCluster.lpush("producerList","value_" + uuid);
        return uuid;
    }
}

package com.lch.ccims.redismq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping("/redis")
@Controller
public class RedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/index")
    public String redisIndex() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("user", "user2");
        String str = ops.get("a");
        System.out.println("redis server str:" + str);
        return null;
    }

    /**
     * 发布消息
     * @param id
     * @return
     */
    @RequestMapping("/sendMessage/{id}")
    public String sendMessage(@PathVariable String id) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.convertAndSend("msg","哈哈哈，redis 订阅信息: [s%]" + uuid);
        return "";
    }
}

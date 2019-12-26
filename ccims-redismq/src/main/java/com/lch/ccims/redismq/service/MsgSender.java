package com.lch.ccims.redismq.service;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * 生产者
 */

public class MsgSender {

    private RedisTemplate<Object, Object> redisTemplate;

    public MsgSender(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMsg(String queue, Object msg) {
        redisTemplate.opsForList().leftPush(queue, msg);
        System.out.println(msg);
    }
}

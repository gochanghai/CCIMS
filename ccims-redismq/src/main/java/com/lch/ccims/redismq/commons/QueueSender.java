package com.lch.ccims.redismq.commons;

import org.springframework.data.redis.core.RedisTemplate;

public class QueueSender {

    private RedisTemplate<Object, Object> redisTemplate;

    public QueueSender(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMsg(String queue, Object msg) {
        redisTemplate.opsForList().leftPush(queue, msg);

    }
}

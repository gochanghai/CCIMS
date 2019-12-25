package com.lch.ccims.redismq.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {

    @Autowired
    private JedisPool jedisPool;


}

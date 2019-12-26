package com.lch.ccims.redismq.config;

import com.lch.ccims.redismq.service.MsgConsumerImpl;
import com.lch.ccims.redismq.service.MsgSender;
import com.lch.ccims.redismq.service.RedisMqConsumerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 设置Key使用String序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    /**
     * 配置redis消息队列生产者
     *
     * @param redisTemplate redis
     * @return 生产者
     */
    @Bean
    public MsgSender msgSender(@Autowired RedisTemplate<Object, Object> redisTemplate) {
        return new MsgSender(redisTemplate);
    }


    /**
     * 配置redis消息队列消费者容器
     *
     * @param redisTemplate redis
     * @return 消费者容器
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public RedisMqConsumerContainer redisMqConsumerContainer(@Autowired RedisTemplate<Object, Object> redisTemplate) {
        RedisMqConsumerContainer config = new RedisMqConsumerContainer(redisTemplate);
        config.addConsumer(QueueConfiguration.builder()
                .queue("TEST")
                .consumer(new MsgConsumerImpl())
                .build());
        return config;
    }
}

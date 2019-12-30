package com.lch.ccims.redismq.config;

import com.lch.ccims.redismq.commons.RedisMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 消息队列  订阅者  redis配置
 */
//@Configuration
//@AutoConfigureAfter({RedisMessage.class})
public class RedisSubConfig {

    /**
     * 创建连接工厂
     *
     * @param connectionFactory
     * @param adapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter adapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(adapter, new PatternTopic("msg"));
        return container;
    }

    /**
     * @param message
     * @return
     */
    @Bean
    public MessageListenerAdapter adapter(RedisMessage message) {
        // onMessage 如果RedisMessage 中 没有实现接口，这个参数必须跟RedisMessage中的读取信息的方法名称一样
        return new MessageListenerAdapter(message, "onMessage");
    }
}

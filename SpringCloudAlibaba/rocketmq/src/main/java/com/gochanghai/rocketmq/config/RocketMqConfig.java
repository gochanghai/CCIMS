package com.gochanghai.rocketmq.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import com.gochanghai.rocketmq.config.RocketMqConfig.MySource;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;

/**
 * 配置消息生产者
 */
@EnableBinding({MySource.class})
public class RocketMqConfig {
    public interface MySource {
        @Output(Source.OUTPUT)
        MessageChannel output();
    }
}

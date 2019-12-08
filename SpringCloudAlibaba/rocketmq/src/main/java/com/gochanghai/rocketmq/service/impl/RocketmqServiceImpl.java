package com.gochanghai.rocketmq.service.impl;

import com.gochanghai.rocketmq.config.RocketMqConfig.MySource;
import com.gochanghai.rocketmq.service.RocketmqService;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class RocketmqServiceImpl implements RocketmqService{

    @Autowired
    private MySource source;

    /**
     * 发送字符消息
     * @param meaasge
     */
    @Override
    public void send(String meaasge) {
        source.output().send(MessageBuilder.withPayload(meaasge).build());
    }

    /**
     * 发送带tag的对象消息
     */
    public <T> void sendWithTags(T msg, String tag) {
        Message message = MessageBuilder.withPayload(msg)
                .setHeader(MessageConst.PROPERTY_TAGS, tag)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build();
        source.output().send(message);
    }

}

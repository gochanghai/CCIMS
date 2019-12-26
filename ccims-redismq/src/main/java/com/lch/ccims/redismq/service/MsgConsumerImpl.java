package com.lch.ccims.redismq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者接口实现
 */
public class MsgConsumerImpl implements MsgConsumer {

    private static Logger log = LoggerFactory.getLogger(MsgConsumerImpl.class);

    @Override
    public void onMessage(Object message) {
        log.info("收到消息:" + message);
    }

    @Override
    public void onError(Object msg, Exception e) {
        log.error("发生错误,消息:{}", msg, e);
    }
}

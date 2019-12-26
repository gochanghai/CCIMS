package com.lch.ccims.redismq.service;


/**
 * 消费者接口
 */
public interface MsgConsumer {

    void onMessage(Object message);

    void onError(Object msg, Exception e);
}

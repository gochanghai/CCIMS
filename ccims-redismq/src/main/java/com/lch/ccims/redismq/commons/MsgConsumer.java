package com.lch.ccims.redismq.commons;

public interface MsgConsumer {

    void onMessage(Object message);

    void onError(Object msg, Exception e);
}

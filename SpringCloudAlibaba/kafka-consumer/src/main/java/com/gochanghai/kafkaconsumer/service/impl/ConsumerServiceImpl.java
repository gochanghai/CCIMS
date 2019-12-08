package com.gochanghai.kafkaconsumer.service.impl;

import com.gochanghai.kafkaconsumer.service.ConsumerService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @KafkaListener(topics = "test",groupId = "test-group")
    public void receive(String message){
        System.out.println("test--消费消息:" + message);
    }
}

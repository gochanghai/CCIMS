package com.gochanghai.kafkaproducer.service.impl;

import com.gochanghai.kafkaproducer.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Slf4j
@Service
@EnableScheduling
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 定时任务1
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void send(){
        String message = UUID.randomUUID().toString();
        ListenableFuture future = kafkaTemplate.send("test", message);
        future.addCallback(o -> System.out.println("消息发送成功：" + message), throwable -> System.out.println("消息发送失败：" + message));
        log.info(message);
    }
}

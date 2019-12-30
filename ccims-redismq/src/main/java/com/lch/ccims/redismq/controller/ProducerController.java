package com.lch.ccims.redismq.controller;

import com.lch.ccims.redismq.service.MsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("producer")
public class ProducerController {

    @Autowired
    private MsgSender msgSender;

    /**
     * 发布消息
     *
     * @return
     */
    @PostMapping("sendMessage")
    public String sendMessage(@RequestBody String dto) {

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        msgSender.sendMsg("TEST","哈哈哈，redis 订阅信息: [s%]" + dto);
        System.out.println(uuid);
        System.out.println(dto);
        return "ok" + uuid;
    }
}



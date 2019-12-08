package com.gochanghai.websocket;

import com.gochanghai.websocket.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/webSocket")
public class WebSocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 群发消息内容
     * @param message
     * @return
     */
    @RequestMapping(value="/sendAll", method= RequestMethod.GET)
    public String sendAllMessage(@RequestParam(required=true) String message){
        webSocketServer.sendAllMessage(message);
        return "ok";
    }

    /**
     * 指定会话ID发消息
     * @param message 消息内容
     * @param id 连接会话ID
     * @return
     */
    @RequestMapping(value="/sendOne", method= RequestMethod.GET)
    public String sendOneMessage(@RequestParam(required=true) String message, @RequestParam(required=true) String id){
        try {
            webSocketServer.sendMessage(message,id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}

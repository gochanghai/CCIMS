package com.lch.ccims.demo.controller;

import com.lch.ccims.demo.common.RedisService;
import com.lch.ccims.demo.common.annotation.Log;
import com.lch.ccims.demo.common.annotation.RedisCache;
import com.lch.ccims.demo.common.annotation.UserInfo;
import com.lch.ccims.demo.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("dict")
public class DictController {

    @Autowired
    private RedisService redisService;

    @Log(value = "获取字典值")
    @RedisCache(value = "获取字典值")
    @PostMapping("get")
    public String getDictValueByKey(@RequestBody Map<String,String> dto) {

        return "dict";
    }

    @Log(value = "保存字典值")
    @UserInfo
    @PostMapping("save")
    public String saveDictValueByKey(@RequestBody BaseEntity dto) {
        System.out.println(dto.toString());
        return "save dict";
    }

    @Log(value = "每次用户登录时会执行一次")
    @PostMapping("login")
    public String userLogin(@RequestBody BaseEntity dto) {
        redisService.setActiveUserCount(dto.getUserId());
        return "用户登录成功";
    }
    @Log(value = "获取7天内活跃用户统计")
    @PostMapping("getActiveUserCount")
    public Object getActiveUserCount(@RequestBody BaseEntity dto) {
        Map<String, Long> result = new HashMap<>();
        long activeUserCount = redisService.getActiveUserCount();
        result.put("2020-03-08",(long)activeUserCount);
        return result;
    }

    @Log(value = "获取7天内活跃用户统计")
    @PostMapping("getActiveUserCount7")
    public Object getActiveUserCount7(@RequestBody BaseEntity dto) {
        Map<String, Long> result = new HashMap<>();
        long activeUserCount = redisService.bitOp(RedisStringCommands.BitOperation.OR,"2020-03-09","2020-03-08","2020-03-07");
        result.put("2020-03-08",(long)activeUserCount);
        return result;
    }

    @Log(value = "获取7天内活跃用户统计")
    @PostMapping("getActiveUserCount72")
    public Object getActiveUserCount72(@RequestBody BaseEntity dto) {
        Map<String, Long> result = new HashMap<>();
        long activeUserCount = redisService.bitCount("activeUser:"+"2020-03-09");
        result.put("2020-03-09",(long)activeUserCount);
        return result;
    }

}

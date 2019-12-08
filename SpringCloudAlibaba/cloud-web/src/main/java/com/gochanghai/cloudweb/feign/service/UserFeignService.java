package com.gochanghai.cloudweb.feign.service;

import com.gochanghai.cloudweb.feign.fallback.UserFeignFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "basic-servers", contextId = "user", fallback = UserFeignFallback.class)
public interface UserFeignService {

    @PostMapping("/user/test")
    String test(@RequestBody Map<String, Object> dto);
}

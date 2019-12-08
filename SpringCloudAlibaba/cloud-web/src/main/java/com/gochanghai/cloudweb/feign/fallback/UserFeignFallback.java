package com.gochanghai.cloudweb.feign.fallback;

import com.gochanghai.cloudweb.feign.service.UserFeignService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
public class UserFeignFallback implements UserFeignService {

    @Override
    public String test(@RequestBody Map<String, Object> dto) {
        return "test fallback";
    }
}

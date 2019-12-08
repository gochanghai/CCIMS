package com.gochanghai.springsecurity.security;

import com.gochanghai.springsecurity.common.config.JWTConfig;
import com.gochanghai.springsecurity.common.util.JWTTokenUtil;
import com.gochanghai.springsecurity.common.util.ResultUtil;
import com.gochanghai.springsecurity.common.vo.SelfUserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description 登录成功处理类
 * @Author changhai.liu
 * @CreateTime 2019/10/25 9:13
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 登录成功返回结果
     * @Author changhai.liu
     * @CreateTime 2019/10/25 9:27
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        // 组装JWT
        SelfUserVo selfUserVo =  (SelfUserVo) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(selfUserVo);
        token = JWTConfig.tokenPrefix + token;
        // 封装返回参数
        Map<String,Object> resultData = new HashMap<>();
        resultData.put("code","200");
        resultData.put("msg", "登录成功");
        resultData.put("token",token);
        ResultUtil.responseJson(response,resultData);
    }
}

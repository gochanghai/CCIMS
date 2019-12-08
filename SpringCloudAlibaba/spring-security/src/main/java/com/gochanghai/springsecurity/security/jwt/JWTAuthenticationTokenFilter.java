package com.gochanghai.springsecurity.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.gochanghai.springsecurity.common.config.JWTConfig;
import com.gochanghai.springsecurity.common.vo.SelfUserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 * @Author Sans
 * @CreateTime 2019/10/25 16:41
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获得TokenHeader
        String tokenHeader = request.getHeader(JWTConfig.tokenHeader);
        if (null!=tokenHeader && tokenHeader.startsWith(JWTConfig.tokenPrefix)) {
            try {
                // 获取请求头中JWT的Token
                if (!StringUtils.isEmpty(request.getHeader(JWTConfig.tokenHeader))) {
                    // 截取JWT前缀
                    String token = request.getHeader(JWTConfig.tokenHeader).replace(JWTConfig.tokenPrefix, "");
                    // 解析JWT
                    Claims claims = Jwts.parser()
                            .setSigningKey(JWTConfig.secret)
                            .parseClaimsJws(token)
                            .getBody();
                    // 获取用户名
                    String username = claims.getSubject();
                    String userId=claims.getId();
                    if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(userId)) {
                        // 获取角色
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        String authority = claims.get("authorities").toString();
                        if(!StringUtils.isEmpty(authority)){
                            List<Map<String,String>> authorityMap = JSONObject.parseObject(authority, List.class);
                            for(Map<String,String> role : authorityMap){
                                if(Objects.nonNull(role) && !StringUtils.isEmpty(role.get("authority"))) {
                                    authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                                }
                            }
                        }
                        //组装参数
                        SelfUserVo SelfUserVo = new SelfUserVo();
                        SelfUserVo.setUsername(claims.getSubject());
                        SelfUserVo.setUserId(Long.parseLong(claims.getId()));
                        SelfUserVo.setAuthorities(authorities);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(SelfUserVo, userId, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (ExpiredJwtException e){
                log.info("Token过期");
            } catch (Exception e) {
                log.info("Token无效");
            }
        }
        filterChain.doFilter(request, response);
        return;
    }
}

package com.gochanghai.springsecurity.security;

import com.gochanghai.springsecurity.common.vo.SelfUserVo;
import com.gochanghai.springsecurity.entity.SysUserEntity;
import com.gochanghai.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity用户的业务实现
 * @Author Sans
 * @CreateTime 2019/10/25 17:21
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户信息
     * @Author Sans
     * @CreateTime 2019/9/13 17:23
     * @Param  username  用户名
     * @Return UserDetails SpringSecurity用户信息
     */
    @Override
    public SelfUserVo loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户信息
        SysUserEntity sysUserEntity =sysUserService.selectUserByName(username);
        if (sysUserEntity!=null){
            // 组装参数
            SelfUserVo selfUserVo = new SelfUserVo();
            selfUserVo.setUserId(sysUserEntity.getUserId());
            selfUserVo.setUsername(sysUserEntity.getUsername());
            selfUserVo.setPassword(sysUserEntity.getPassword());
            selfUserVo.setStatus(sysUserEntity.getStatus());
            return selfUserVo;
        }
        return null;
    }
}

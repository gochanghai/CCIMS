package com.gochanghai.springsecurity.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gochanghai.springsecurity.entity.SysMenuEntity;
import com.gochanghai.springsecurity.entity.SysRoleEntity;
import com.gochanghai.springsecurity.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    /**
     * 通过用户ID查询角色集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<SysRoleEntity> selectSysRoleByUserId(Long userId);
    /**
     * 通过用户ID查询权限集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<SysMenuEntity> selectSysMenuByUserId(Long userId);

}

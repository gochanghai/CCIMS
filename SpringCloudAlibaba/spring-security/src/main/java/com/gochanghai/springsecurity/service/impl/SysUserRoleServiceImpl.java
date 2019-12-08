package com.gochanghai.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gochanghai.springsecurity.dao.SysUserRoleDao;
import com.gochanghai.springsecurity.entity.SysUserRoleEntity;
import com.gochanghai.springsecurity.service.SysUserRoleService;
import org.springframework.stereotype.Service;

@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

}
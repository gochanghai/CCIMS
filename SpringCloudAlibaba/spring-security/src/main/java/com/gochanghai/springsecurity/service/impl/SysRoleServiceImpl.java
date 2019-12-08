package com.gochanghai.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gochanghai.springsecurity.dao.SysRoleDao;
import com.gochanghai.springsecurity.entity.SysRoleEntity;
import com.gochanghai.springsecurity.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

}
package com.gochanghai.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gochanghai.springsecurity.dao.SysRoleMenuDao;
import com.gochanghai.springsecurity.entity.SysRoleMenuEntity;
import com.gochanghai.springsecurity.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao, SysRoleMenuEntity> implements SysRoleMenuService {

}

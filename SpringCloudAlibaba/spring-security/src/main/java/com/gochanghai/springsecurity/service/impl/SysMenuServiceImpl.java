package com.gochanghai.springsecurity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gochanghai.springsecurity.dao.SysMenuDao;
import com.gochanghai.springsecurity.entity.SysMenuEntity;
import com.gochanghai.springsecurity.service.SysMenuService;
import org.springframework.stereotype.Service;

@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {

}
package com.gochanghai.springsecurity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("sys_role_menu")
public class SysRoleMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long menuId;
}

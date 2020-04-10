package com.lch.ccims.demo.entity;

import lombok.Data;

@Data
public class BaseEntity {

    private Long userId;

    private String creator;

    private String modifier;

    //private Date gmtCreate;

    //private Date gmtModified;
}

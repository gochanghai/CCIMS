package com.lch.ccims.demo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserVO {

    private String username;

    private String nikeName;

    private List<Long> roleIds = new ArrayList<>();

    private String url;

    private String token;
}

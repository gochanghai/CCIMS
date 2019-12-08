package com.changhi.changhaioss.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface AliyunService {

    String uploadFile(MultipartFile file);

    String downloadFile(String fileName, HttpServletResponse response);
}

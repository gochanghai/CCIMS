package com.changhi.changhaioss.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public interface QiniuService {

    String uploadFile(File file) throws QiniuException;

    String uploadFile(InputStream inputStream) throws QiniuException;

    Response delete(String key) throws QiniuException;

    Map<String, Object> getToken();
}

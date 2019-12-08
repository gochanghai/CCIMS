package com.changhi.changhaioss.controller;

import com.changhi.changhaioss.service.QiniuService;
import com.qiniu.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/qiniu/")
public class QiniuController {

    @Autowired
    private QiniuService qiniuService;

    /**
     * 以流的形式上传图片
     *
     * @param file
     * @return 返回访问路径
     * @throws IOException
     */
    @PostMapping("upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return qiniuService.uploadFile(file.getInputStream());
    }

    /**
     * 删除文件
     *
     * @param key
     * @return
     * @throws IOException
     */
    @GetMapping("delete/{key}")
    public Response deleteFile(@PathVariable String key) throws IOException {
        return qiniuService.delete(key);
    }

    /**
     * 获取 token
     *
     * @return
     */
    @GetMapping("getToken")
    public Map<String, Object> getToken() {
        return qiniuService.getToken();
    }
}
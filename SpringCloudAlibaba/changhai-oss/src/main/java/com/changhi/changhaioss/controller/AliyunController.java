package com.changhi.changhaioss.controller;

import com.changhi.changhaioss.service.AliyunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/oss/aliyun/")
public class AliyunController {

    @Autowired
    private AliyunService aliyunService;

    @PostMapping("upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file){
        return aliyunService.uploadFile(file);
    }

    @PostMapping("download")
    public String downloadFile(@RequestBody Map<String, String> dto, HttpServletResponse response){
        return aliyunService.downloadFile(dto.get("fileName"), response);
    }
}

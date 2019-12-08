package com.changhi.changhaioss.service.impl;

import com.alibaba.fastjson.JSON;
import com.changhi.changhaioss.config.QiniuProperties;
import com.changhi.changhaioss.service.QiniuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@EnableConfigurationProperties(QiniuProperties.class)
public class QiniuServiceImpl implements QiniuService, InitializingBean {

    @Autowired
    private QiniuProperties qiniuProperties;

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    // 定义七牛云上传的相关策略
    private StringMap putPolicy;


    /**
     * 以文件的形式上传
     *
     * @param file
     * @return
     * @throws QiniuException
     */
    @Override
    public String uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, null, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        //解析结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        String return_path = qiniuProperties.getPrefix() + "/" + putRet.key;
        log.info("文件名称={}", return_path);
        return return_path;
    }

    /**
     * 以流的形式上传
     *
     * @param inputStream
     * @return
     * @throws QiniuException
     */
    @Override
    public String uploadFile(InputStream inputStream) throws QiniuException {
        // 新文件名,key 为 null 以文件内容的hash值作为文件名
        String key =  UUID.randomUUID().toString();
        log.info("key",key);
        String token = getUploadToken();
        Response response = this.uploadManager.put(inputStream, key, token, null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, key, token, null, null);
            retry++;
        }
        //解析结果
        DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        log.info("response={}",response.bodyString());
        String return_path = qiniuProperties.getPrefix() + "/" + putRet.key;
        log.info("文件名称={}", return_path);
        return return_path;
    }

    /**
     * 删除七牛云上的相关文件
     *
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(qiniuProperties.getBucket(), key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = bucketManager.delete(qiniuProperties.getBucket(), key);
        }
        return response;
    }

    @Override
    public Map<String, Object> getToken() {
        String token = getUploadToken();
        Map<String, Object> result = new HashMap<>();
        result.put("token",token);
        return result;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        // 自定义文件名字
        // putPolicy.put("saveKey", UUID.randomUUID().timestamp());
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(qiniuProperties.getBucket(), null, 3600, putPolicy);
    }
}

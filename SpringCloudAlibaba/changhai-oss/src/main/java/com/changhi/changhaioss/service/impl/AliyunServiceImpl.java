package com.changhi.changhaioss.service.impl;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.changhi.changhaioss.config.AliyunProperties;
import com.changhi.changhaioss.service.AliyunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@EnableConfigurationProperties(AliyunProperties.class)
public class AliyunServiceImpl implements AliyunService, InitializingBean {

    private static String FILE_URL;

    @Autowired
    private AliyunProperties aliyunProperties;
    // 文件名
    String objectName = null;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("aliyun-oss 配置初始化成功");
    }


    /**
     * 上传文件
     * @param file
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        log.info("文件上传");
        String filename = file.getOriginalFilename();
        log.info("文件名: ",filename);
        try {

            if (file != null) {
                if (!"".equals(filename.trim())) {
                    File newFile = new File(filename);
                    FileOutputStream os = new FileOutputStream(newFile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newFile);
                    // 上传到OSS
                    String uploadUrl = upLoad(newFile);
                    return uploadUrl;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "success";
    }

    /**
     * 下载文件
     * @param objectName
     * @return
     */
    @Override
    public String downloadFile(String objectName, HttpServletResponse response) {
        //通知浏览器以附件形式下载
        try {
            String[] strings = objectName.split("-");
            String fileName = new String(strings[1].getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + fileName);
            // 设置强制下载不打开
//            response.setContentType("application/force-download");
            // 设置文件名
//            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            ServletOutputStream outputStream = response.getOutputStream();
            OSS ossClient = getOSSClient();
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(aliyunProperties.getBucketName(), objectName);
            // 读取文件内容。
            BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while ((lenght = in.read(buffer)) != -1) {
                out.write(buffer, 0, lenght);
            }
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
            return "下载成功";
        } catch (Exception e) {
            e.printStackTrace();
            log.error("字符串转换异常:{}", e.getMessage());
        }
        log.info("下载成功");
        return "下载失败";
    }

    /**
     * 上传文件到 aliyun-oss
     * @return
     */
    private String upLoad(File file){
        // 默认值为：true
        boolean isImage = true;
        // 判断所要上传的图片是否是图片，图片可以预览，其他文件不提供通过URL预览
        try {
            Image image = ImageIO.read(file);
            isImage = image == null ? false : true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("------OSS文件上传开始--------" + file.getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String dateStr = format.format(new Date());
        // 判断文件
        if (file == null) {
            return null;
        }
        // 创建OSSClient实例。
        OSSClient ossClient = getOSSClient();
        try {
            // 判断容器是否存在,不存在就创建
            if (!ossClient.doesBucketExist(aliyunProperties.getBucketName())) {
                ossClient.createBucket(aliyunProperties.getBucketName());
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunProperties.getBucketName());
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 设置文件路径和名称
            String fileUrl = (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            //如果是图片，则图片的URL为
            if (isImage) {
                FILE_URL = "https://" + aliyunProperties.getBucketName() + "." + aliyunProperties.getEndpoint() + "/" + fileUrl;
            } else {
                FILE_URL = "非图片，不可预览。文件路径为：" + fileUrl;
            }
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(aliyunProperties.getBucketName(), fileUrl, file));
            // 设置权限(公开读)
            ossClient.setBucketAcl(aliyunProperties.getBucketName(), CannedAccessControlList.PublicRead);
            if (result != null) {
                log.info("------OSS文件上传成功------" + fileUrl);
            }
            // 保存文件信息到数据库


        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getErrorMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return FILE_URL;
    }


    /**
     * 下载 aliyun-oss 上的文件
     * @param objectName 从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
     * @return
     */
    public  void download(String objectName) {
        // 创建OSSClient实例。
        OSSClient ossClient = getOSSClient();
        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(aliyunProperties.getBucketName(), objectName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        // 对文件进行处理

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 初始化 OSSClient
     * @return
     */
    private OSSClient getOSSClient() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(
                aliyunProperties.getEndpoint(),
                aliyunProperties.getAccessKeyId(),
                aliyunProperties.getAccessKeySecret()
        );
        return (OSSClient) ossClient;
    }

}

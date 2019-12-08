package com.gochanghai.basicservers.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.gochanghai.basicservers.dto.UserDTO;
import com.gochanghai.basicservers.model.ExportExcelModel;
import com.gochanghai.basicservers.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("test")
    public String test(@RequestBody Map<String, Object> dto) {
        return "Hello Nacos Discovery " + dto.get("message");
    }

    @PostMapping("login")
    public String login(@Valid @RequestBody Map<String, Object> dto, BindingResult result) {
        return "Hello Nacos Discovery " + dto.get("message");
    }

    @PostMapping("add")
//    @Validated
    public String login(@Valid @RequestBody UserDTO dto) {
        return "Hello Nacos Discovery ";
    }

    @PostMapping("exportExcel")
    public String exportExcel(@Valid @RequestBody Map<String, Object> dto, BindingResult result) {

        List<ExportExcelModel> dataList = new ArrayList<>();
        for (var i = 0; i < 2000; i++){
            ExportExcelModel model = new ExportExcelModel();
            model.setName("刘长海" + i);
            model.setSax("M");
            model.setAddress("222222222222wwww");
            model.setEmail("gochanghai@aliyun.com");
            model.setLast("1");
            model.setAge(i);
            model.setSchool("学校" + i);
            dataList.add(model);
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream("H:\\1001.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,ExportExcelModel.class);
            writer.write(dataList, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return "Hello Nacos Discovery " + dto.get("message");
    }

    @PostMapping("importExcel")
    public String importExcel(@Valid @RequestBody Map<String, Object> dto, BindingResult result) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("H:\\1001.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(2, 1, ExportExcelModel.class));
        System.out.println(data.toString());
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hello Nacos Discovery " + dto.get("message");
    }

    @PostMapping("testRedisPut")
    public String testRedisPut() {
        Map<String,Object> maps = new HashMap<>();
        maps.put("1",1);
        maps.put("2","12");
        maps.put("3",2);
        maps.put("4",232424L);
        maps.put("5","中国");
        redisUtil.hmset("maps",maps);
        List<Object> list = new ArrayList<>();
        list.add("1");
        list.add(2);
        list.add("12");
        list.add("1234");
        list.add("124232323");
        redisUtil.lSet("list",list, 9000);

        ArrayList<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            var userDTO = new UserDTO();
            userDTO.setGender("M");
            userDTO.setIdCard("12" + i);
            userDTO.setName("我爱你中国");
            users.add(userDTO);
        }
        Map<String, Object> listMap = users.stream().collect(Collectors.toMap(UserDTO::getIdCard,user -> user));
        redisUtil.del("user");
        redisUtil.hmset("user",listMap);
        return "Hello Nacos Discovery ";
    }

    @PostMapping("testRedisGet")
    public Object testRedisGet() {
//        return redisUtil.hmget("maps").toString();
        UserDTO user = (UserDTO) redisUtil.hget("user", "120");
        return user;
//        return redisUtil.hget("maps","5").toString();
    }

    /**
     * 文件下载
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ExportExcelModel}
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应下载的文件名不对。这个时候 请别使用swagger 他会有影响
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), ExportExcelModel.class).sheet("模板").doWrite(data());
    }

    private List<ExportExcelModel> data() {
        List<ExportExcelModel> list = new ArrayList<ExportExcelModel>();
        for (int i = 0; i < 10; i++) {
            ExportExcelModel data = new ExportExcelModel();
            data.setName("字符串" + 0);
            data.setSchool("wewe");
            data.setSax("wwww");
            list.add(data);
        }
        return list;
    }

}

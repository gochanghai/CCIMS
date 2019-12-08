package com.gochanghai.basicservers.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

@Data
public class ExportExcelModel extends BaseRowModel {

    @ExcelProperty(value = {"表头1","姓名","姓名"} ,index = 0)
    private String name;

    @ExcelProperty(value = {"表头1","年龄","年龄"},index = 1)
    private Integer age;

    @ExcelProperty(value = {"邮箱","邮箱","邮箱"},index = 2)
    private String email;

    @ExcelProperty(value = {"其他信息","表头1","地址"},index = 3)
    private String address;

    @ExcelProperty(value = {"其他信息","表头1","性别"},index = 4)
    private String sax;

    @ExcelProperty(value = {"其他信息","表头2","高度"},index = 5)
    private String heigh;

    @ExcelProperty(value = {"其他信息","表头2","备注"},index = 6)
    private String last;

    @ExcelProperty(value = {"其他信息","表头3","学校"},index = 7)
    private String school;
}

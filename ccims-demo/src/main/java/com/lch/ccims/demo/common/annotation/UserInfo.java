package com.lch.ccims.demo.common.annotation;


import java.lang.annotation.*;

/**
 * 统一处理修改人、创建人、修改时间、创建时间的 截注解
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
@Documented
public @interface UserInfo {

}

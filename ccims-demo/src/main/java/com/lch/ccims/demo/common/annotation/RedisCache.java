package com.lch.ccims.demo.common.annotation;


import java.lang.annotation.*;

/**
 * RedisCache注解
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
@Documented
public @interface RedisCache {

    String value() default "";

}

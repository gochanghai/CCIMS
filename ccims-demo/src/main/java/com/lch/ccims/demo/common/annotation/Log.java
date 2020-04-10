package com.lch.ccims.demo.common.annotation;

import java.lang.annotation.*;

/**
 * 日记注解
 */
@Target({ElementType.TYPE, ElementType.METHOD}) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
@Documented
public @interface Log {

    String value() default "";

}

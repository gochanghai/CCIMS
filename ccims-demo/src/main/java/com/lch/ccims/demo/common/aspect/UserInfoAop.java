package com.lch.ccims.demo.common.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * AOP实现Redis缓存处理
 */
@Component
@Aspect
public class UserInfoAop {

    private static final String CREATOR     = "creator";
    private static final String MODIFIER    = "modifier";
    private static final String GMTCREATE   = "gmtCreate";
    private static final String GMTMODIFIED = "gmtModified";

    @Pointcut("@annotation(com.lch.ccims.demo.common.annotation.UserInfo)") // 表示匹配带有自定义注解的方法
    public void pointcut() {}

    @Before("pointcut()")
    public void beforeInsert(JoinPoint jp) {
        Object[] args = jp.getArgs();

        if (args != null && args.length > 0) {
            Object argument = args[0];
            BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
            // 设置创建时间和修改时间
            if (beanWrapper.isWritableProperty(GMTCREATE)) {
                beanWrapper.setPropertyValue(GMTCREATE, new Date());
            }
            if (beanWrapper.isWritableProperty(GMTMODIFIED)) {
                beanWrapper.setPropertyValue(GMTMODIFIED, new Date());
            }
            // 设置创建人和修改人
//            if (beanWrapper.isWritableProperty(CREATOR) && privilegeInfo != null
//                    && StringUtils.isNotBlank(privilegeInfo.getWorkNo())) {
//                beanWrapper.setPropertyValue(CREATOR, privilegeInfo.getWorkNo());
//            }
//            if (beanWrapper.isWritableProperty(MODIFIER) && privilegeInfo != null
//                    && StringUtils.isNotBlank(privilegeInfo.getWorkNo())) {
//                beanWrapper.setPropertyValue(MODIFIER, privilegeInfo.getWorkNo());
//            }
        }
    }
}

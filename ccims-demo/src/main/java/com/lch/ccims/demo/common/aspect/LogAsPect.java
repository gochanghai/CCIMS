package com.lch.ccims.demo.common.aspect;

import com.lch.ccims.demo.common.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Promise
 * @createTime 2018年12月18日 下午9:33:28
 * @description 切面日志配置
 */
@Aspect
@Component
public class LogAsPect {

    private final static Logger log = org.slf4j.LoggerFactory.getLogger(LogAsPect.class);

//    @Autowired
//    private ISysLogService sysLogService;

    @Pointcut("@annotation(com.lch.ccims.demo.common.annotation.Log)") // 表示匹配带有自定义注解的方法
    public void pointcut() {}

    /**
     * 切入点 2
     */
    @Pointcut("execution(public * com.lch.ccims.demo.controller..*.*(..))") // 表示匹配controller包及其子包下的所有公有方法
    public void pointcutController() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result =null;
        long beginTime = System.currentTimeMillis();

        try {
            log.info("我在目标方法之前执行！");
            result = point.proceed();
            long endTime = System.currentTimeMillis();
            //insertLog(point,endTime-beginTime);
        } catch (Throwable e) {
            // TODO Auto-generated catch block
        }
        return result;
    }

    @Before("pointcutController()")
    public void around2(JoinPoint point) {
        //获取目标方法
        String methodNam = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();
        /// 获取方法签名
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        Log logAnno = method.getAnnotation(Log.class);
        // 获取操作描述的属性值
        //String operateType = logAnno.value();
        //获取方法参数
        String params = Arrays.toString(point.getArgs());

        log.info("get in {} params :{} operateType{}", methodNam, params);
    }
}

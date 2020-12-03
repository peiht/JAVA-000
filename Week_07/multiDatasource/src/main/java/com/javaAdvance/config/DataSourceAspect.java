package com.javaAdvance.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author hitopei
 *
 * 切面，用来管理那些需要进行数据源切换
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    /**
     * 通过注解来管理那些需要动态切换数据源
     */
    @Pointcut("execution(* com.javaAdvance.service..*.*(..))")
    public void aspect(){}

    @Before("aspect()")
    private void before(JoinPoint joinPoint){
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?> clazz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getParameterTypes();

        try {
            Method method1 = clazz.getMethod(method, parameterTypes);
            if (method1.isAnnotationPresent(MyDataSource.class)){
                MyDataSource dataSource = method1.getAnnotation(MyDataSource.class);
                //输出方法和对应执行的数据源
                log.info("方法{},数据源的类型为{}", method, dataSource.value().getName());
                DataSourceContextHolder.putDataSource(dataSource.value().getName());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @After("aspect()")
    private void after(JoinPoint joinPoint){
        DataSourceContextHolder.remove();
    }
}

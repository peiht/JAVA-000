package io.kimmking.rpcfx.demo.provider.aop;

import io.kimmking.rpcfx.api.ServiceProviderDesc;
import io.kimmking.rpcfx.demo.provider.ZookeeperRegister;
import io.kimmking.rpcfx.demo.provider.zookeeper.ZookeeperProperties;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * @author hitopei
 *
 * aop
 */
@Aspect
@Component
public class ZookeeperAspect {
    @Autowired
    private ZookeeperProperties properties;

//    @Pointcut("execution(* io.kimmking.rpcfx.demo.provider..*.*Service(..))")
//    public void aspect(){}

    @After("execution(* io.kimmking.rpcfx.demo.provider..*.*Service(..)) && @annotation(zookeeperRegister)")
    private void before(JoinPoint joinPoint, ZookeeperRegister zookeeperRegister) {
        Object target = joinPoint.getTarget();
        String method = joinPoint.getSignature().getName();
        Class<?> clazz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature())
                .getMethod().getParameterTypes();

        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder().
                connectString(properties.getHost() + ":" + properties.getPort().toString()).
                namespace(properties.getNamespace()).retryPolicy(retryPolicy).build();
        client.start();
        System.out.println("注册到zookeeper");
        try {
            Method method1 = clazz.getMethod(method, parameterTypes);
            System.out.println(method1.getName());
            System.out.println(method1.isAnnotationPresent(Bean.class));
            System.out.println(method1.isAnnotationPresent(ZookeeperRegister.class));
            //if (method1.isAnnotationPresent(ZookeeperRegister.class)) {
                String service = method1.getName();
                ServiceProviderDesc userServiceDesc = ServiceProviderDesc.builder()
                        .host(InetAddress.getLocalHost().getHostAddress())
                        .port(8080).serviceClass(service).build();

                try {
                    if ( null == client.checkExists().forPath("/" + service)) {
                        client.create().withMode(CreateMode.PERSISTENT).forPath("/" + service, "service".getBytes());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                client.create().withMode(CreateMode.EPHEMERAL).
                        forPath( "/" + service + "/" + userServiceDesc.getHost() + "_" + userServiceDesc.getPort(), "provider".getBytes());
           // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

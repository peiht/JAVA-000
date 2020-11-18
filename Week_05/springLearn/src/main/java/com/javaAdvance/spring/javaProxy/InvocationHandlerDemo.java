package com.javaAdvance.spring.javaProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * java自带proxy实现动态代理
 * @author hitopei
 */
public class InvocationHandlerDemo implements InvocationHandler {

    public Object object;
    public InvocationHandlerDemo(Object obj) {
        this.object = obj;
    }

    public Hello getHello(){
        return (Hello)Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before execute");
        System.out.println("method :" + method);
        method.invoke(object, args);
        System.out.println("after execute");
        return null;
    }
}

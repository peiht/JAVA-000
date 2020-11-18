package com.javaAdvance.spring.javaProxy;

import java.lang.reflect.Proxy;

public class TestAop {
    public static void main(String[] args) {
        Hello hello = new HelloImpl();
        InvocationHandlerDemo handler = new InvocationHandlerDemo(hello);

        Hello hello1 = handler.getHello();

        Hello hello2 = (Hello)Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);

        hello1.sayHello();

        hello2.sayHello();
    }
}

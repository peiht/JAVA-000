package com.javaAdvance.spring.javaProxy;

/**
 * @author hitopei
 *
 * 接口实现类
 */
public class HelloImpl implements Hello{
    @Override
    public String sayHello() {
        System.out.println("say hello to the world");
        return "Hello world";
    }
}

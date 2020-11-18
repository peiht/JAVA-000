package com.javaAdvance.spring.BeanLoad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注解配置bean
 * @author hitopei
 */
@Configuration
public class BeanConfiguration {
    @Bean
    public AnnotationBeanDemo getBeanDemo(){
        AnnotationBeanDemo demo = new AnnotationBeanDemo();
        demo.setId(2);
        demo.setName("hello annotation");
        return demo;
    }
}

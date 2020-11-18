package com.javaAdvance.spring.BeanLoad;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BeanLoadingDemo {

    public static void main(String[] args) {

        // xml配置的bean加载
        BeanFactory classPathXmlApplicationContext = new ClassPathXmlApplicationContext("/META-INF/spring-bean.xml");
        BeanDemo beanDemo = classPathXmlApplicationContext.getBean(BeanDemo.class);
        beanDemo.print();

        //注解加载bean
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(AnnotationBeanDemo.class);
        annotationConfigApplicationContext.refresh();
        AnnotationBeanDemo annotationBeanDemo = annotationConfigApplicationContext.getBean(AnnotationBeanDemo.class);
        //调用bean方法
        annotationBeanDemo.print();


        //beandefinition 加载
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BeanDemo.class);
        beanDefinitionBuilder.addPropertyValue("id", 1);
        beanDefinitionBuilder.addPropertyValue("name", "beanDefineName");
        BeanDefinition definition = beanDefinitionBuilder.getBeanDefinition();
        MutablePropertyValues mutablePropertyValues = definition.getPropertyValues();
        String name = mutablePropertyValues.getPropertyValue("name").getValue().toString();
        System.out.println("beanDefine beanDemo = " + name);

    }
}

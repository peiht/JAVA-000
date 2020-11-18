package com.javaAdvance.bootLearn.configuration;

import com.javaAdvance.bootLearn.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BootConfiguration.class)
public class BootAutoConfiguration {

    @Autowired
    private BootConfiguration bootConfiguration;


    @Bean
    @ConditionalOnMissingBean
    public Student getStudent(){
        Student student = new Student();
        student.setName(bootConfiguration.getName());
        student.setId(1);
        //student初始化加载
        student.init();
        System.out.println(student.toString());
        return student;
    }
}

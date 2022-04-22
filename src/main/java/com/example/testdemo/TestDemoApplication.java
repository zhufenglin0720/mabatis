package com.example.testdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 168
 */
@SpringBootApplication
@MapperScan("com.example.service")
@ServletComponentScan("com.example")
@ComponentScan("com.example")
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
public class TestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestDemoApplication.class, args);
    }

}

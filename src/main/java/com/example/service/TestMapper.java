package com.example.service;

/**
 * @author zfl
 * @create 2022/4/12 18:13
 * @description
 */
public interface TestMapper {
    default String a() {
        System.out.println(TestMapper.class);
        return "true";
    }

}

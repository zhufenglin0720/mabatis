package com.example.utils.mybatis;

/**
 * @author zfl
 * @create 2022/1/28 11:52
 * @description
 */
public class Test {

    public static void main(String[] args) {

        String str = "/src/main/java/com/example/pojo";
        System.out.println(str.substring(15).replaceAll("/","."));
    }

}

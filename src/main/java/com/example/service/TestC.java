package com.example.service;

import java.util.concurrent.TimeUnit;

/**
 * @author zfl
 * @create 2022/4/13 10:06
 * @description
 */
public class TestC {

    static boolean flag = true;//共享变量
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (flag){

            }
        }).start();
        TimeUnit.MICROSECONDS.sleep(1);
        flag = false;
        System.out.println("main end");

    }
}

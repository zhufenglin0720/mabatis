package com.example.utils;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author zfl
 * @create 2022/3/25 10:47
 * @description
 */
public class Test {

    static ArrayList<String> arrayList = new ArrayList(1000);
    static LinkedList<String> linkedList = new LinkedList<>();

    static {
        int i = 0;
        while (i < 1000){
            arrayList.add(String.valueOf(i));
            linkedList.add(String.valueOf(i));
            i++;
        }
    }

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
//        byte[] $Proxies = ProxyGenerator.generateProxyClass("$Proxy", new Class[]{Test.class} , 32);
//
//        FileOutputStream src = new FileOutputStream("C:\\Users\\168\\Desktop\\a.class");
//
//        src.write($Proxies);
//
//        src.flush();
//
//        src.close();

        try {
            throw new VerifyError();
        }catch (VerifyError e){
            e.printStackTrace();
        }


        while (true){

        }
    }
}

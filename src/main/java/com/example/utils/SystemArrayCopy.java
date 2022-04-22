package com.example.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author zfl
 * @create 2022/3/24 10:42
 * @description
 */
public class SystemArrayCopy {

    /**
     * 复制Object[] 对target内容进行修改
     * @param source  源数组
     * @param sourceStartIndex 源数组复制开始的下标
     * @param target           目标数组
     * @param targetStartIndex 目标数组开始的下表
     * @param sourceCopyLength 需要拷贝源数组的长度
     * @throws Exception       异常
     */
    public static void arrayCopy(Object[] source,int sourceStartIndex,Object[] target,int targetStartIndex,int sourceCopyLength) throws Exception {
        if(source == null){
            throw new Exception("资源为空");
        }
        boolean validSourceCopyLength = sourceCopyLength < 0 || sourceCopyLength > source.length || sourceStartIndex + sourceCopyLength > source.length;
        if(validSourceCopyLength){
            throw new Exception("拷贝数据长度不合法");
        }
        Object[] targetCopy;
        //如果target为空,targetStartIndex不能为大于0的数
        if(target == null){
            if(targetStartIndex != 0){
                throw new Exception("待拷贝数据起始index不合法");
            }
            target = new Object[sourceCopyLength];
        }
        boolean validTargetStartIndex = targetStartIndex < 0 || targetStartIndex > target.length - 1;
        if(validTargetStartIndex){
            throw new Exception("待拷贝数据起始位置不合法");
        }
        // source [a,b,c] target [a,b,c] -> [c,b,c]  sourceStartIndex 2,targetStartIndex 0,sourceCopyLength 1
        for (int i = targetStartIndex ; i < targetStartIndex + sourceCopyLength ; i++){
            target[i] = source[sourceStartIndex++];
        }
    }

    volatile static Integer count = 0;

    public static void main(String[] args) throws Exception{
//        String[] source = {"a","b","c"};
//        String[] target = new String[4];
//        arrayCopy(source,0, target,0,3);
//        for (String s : target) {
//            System.out.println(s);
//        }
//        System.out.println("--------------------------");
        String[] source1 = {"a","b"};
        Object[] objects = new Object[3];
        System.arraycopy(source1,1,objects,2,1);
        source1[2] = "c";
        for (String s : source1) {
            System.out.println(s);
        }
        ArrayList<Object> a = new ArrayList<>(2);
        a.add("a");
        a.add("b");

        a.add(1,"c");
    }



}

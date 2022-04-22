package com.example.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zfl
 * @create 2022/4/14 14:39
 * @description
 */
public class TestProxy implements InvocationHandler {

    private Object target = null;

    public TestProxy(Object target){
        this.target = target;
    }

    public static void main(String[] args) {

        TestInterface aClass = new TestClass();
        TestProxy testProxy = new TestProxy(aClass);

        TestInterface testInterface = (TestInterface) Proxy.newProxyInstance(aClass.getClass().getClassLoader(), aClass.getClass().getInterfaces(), testProxy);
        float sell = testInterface.sell(10);
        System.out.println(sell);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        result = method.invoke(target,args);
        if(result != null){
            float price = (Float) result;
            price += 25;
            return price;
        }
        return null;
    }



}

interface TestInterface{
    float sell(int amount);
}

class TestClass implements TestInterface{

    @Override
    public float sell(int amount) {
        return 1.0f;
    }
}

package com.example.utils;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * @author zfl
 * @create 2022/1/18 16:31
 * @description
 */
public class AopTargetUtils {

    private static final String CGLIB_FIELD_KEY = "CGLIB$CALLBACK_0";
    private static final String ADVISED_DECLARE_FIELD_KEY = "advised";
    private static final String H_DECLARE_FIELD_KEY = "h";

    /**
     * Gets target.
     *
     * @param proxy the proxy
     * @return the target
     * @throws Exception the exception
     */
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            // 不是代理对象
            return proxy;
        }

        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else {
            return getCglibProxyTargetObject(proxy);
        }

    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField(CGLIB_FIELD_KEY);
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField(ADVISED_DECLARE_FIELD_KEY);
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField(H_DECLARE_FIELD_KEY);
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField(ADVISED_DECLARE_FIELD_KEY);
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }

}

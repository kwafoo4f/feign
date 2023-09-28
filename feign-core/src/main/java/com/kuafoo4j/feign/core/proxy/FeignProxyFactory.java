package com.kuafoo4j.feign.core.proxy;

import java.lang.reflect.Proxy;

/**
 * @description: Feign代理工厂
 * @author: zk
 * @date: 2023-09-22 10:26
 */
public class FeignProxyFactory {

    private FeignInvocationHandler feignInvocationHandler;

    public FeignProxyFactory(FeignInvocationHandler feignInvocationHandler) {
        this.feignInvocationHandler = feignInvocationHandler;
    }

    /**
     * 获取feign接口的代理类
     *
     * @return
     */
    public Object getFeignProxy(Class clazz) {
        try {
            /*
             * JDK动态代理方法
             * ClassLoader loader :类加载器
             * Class<?>[] interfaces:接口的泛型
             *  InvocationHandler h: 增强方法（代理类的方法）
             */
            return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, feignInvocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

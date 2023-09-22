package com.kuafoo4j.feign.core.proxy;


import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.kuafoo4j.feign.core.annatation.FeignClient;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @description: 代理处理
 * @author: zk
 * @date: 2023-09-22 10:32
 */

public class FeignInvocationHandler implements InvocationHandler {

    private RestTemplate restTemplate = new RestTemplate();

    private Object target;

    public FeignInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 方法代理
     * 1.将feign注解解析为请求协议
     * 2.将mvc注解解析为请求方式和参数
     * 3.使用restTemplate发起请求,并解析结果
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping == null) {
            try {
                result =  method.invoke(target, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        FeignClient feignClient = AnnotationUtils.findAnnotation(proxy.getClass(), FeignClient.class);
        String serviceName = feignClient.name();
        Class<?> returnType = method.getReturnType();

        // 构建http请求
        String jsonStr = restTemplate.getForObject(serviceName, String.class, args);

        // 反序列
        if (String.class.equals(returnType)) {
            return jsonStr;
        } else {
           return JSONUtil.toBean(jsonStr,returnType);
        }
    }
}

package com.kuafoo4j.feign.core.proxy;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.kuafoo4j.feign.core.annatation.FeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * @description: 代理处理
 * @author: zk
 * @date: 2023-09-22 10:32
 */
@Slf4j
public class FeignInvocationHandler implements InvocationHandler {

    private RestTemplate restTemplate;

    public FeignInvocationHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
        if ("equals".equals(method.getName())) {
            return this.equals(args);
        }
        if ("hashCode".equals(method.getName())) {
            return this.hashCode();
        }
        if ("toString".equals(method.getName())) {
            return this.toString();
        }

        return httpProxy(proxy, method, args);
    }

    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this,obj);
    }
    @Override
    public int hashCode() {
        return 123123;
    }
    @Override
    public String toString() {
        return "proxy";
    }

    /**
     * http代理接口
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     */
    private Object httpProxy(Object proxy, Method method, Object[] args) {
        Object result = null;
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (getMapping == null) {
            try {
//                result =  method.invoke(target, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        FeignClient feignClient = AnnotationUtils.findAnnotation(proxy.getClass(), FeignClient.class);
        String serviceName = feignClient.name();
        Class<?> returnType = method.getReturnType();

        StringBuffer urlStringBuffer  = new StringBuffer().append("http://").append(serviceName);
        if (!ArrayUtil.isEmpty(getMapping.value())) {
            urlStringBuffer.append("/").append(getMapping.value()[0]);
        }

        String url = urlStringBuffer.toString();
·        log.info("feign http proxy url : {}",  url);
        // 构建http请求
        String jsonStr = args == null ? restTemplate.getForObject(url, String.class)  : restTemplate.getForObject(url, String.class,args);

        // 反序列
        if (String.class.equals(returnType)) {
            return jsonStr;
        } else {
           return JSONUtil.toBean(jsonStr,returnType);
        }
    }
}

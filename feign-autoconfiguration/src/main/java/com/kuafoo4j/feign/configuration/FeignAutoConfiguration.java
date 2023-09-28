package com.kuafoo4j.feign.configuration;

import com.kuafoo4j.feign.core.proxy.FeignInvocationHandler;
import com.kuafoo4j.feign.core.proxy.FeignProxyFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @description: feign自动装配
 * @author: zk
 * @date: 2023-09-28 15:29
 */
@Configuration
public class FeignAutoConfiguration {

    /**
     * http客户端
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * feign方法代理为http请求
     *
     * @param restTemplate
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FeignInvocationHandler feignInvocationHandler(RestTemplate restTemplate) {
        return new FeignInvocationHandler(restTemplate);
    }

    /**
     *  Feign代理对象工厂
     * @param feignInvocationHandler
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FeignProxyFactory feignProxyFactory(FeignInvocationHandler feignInvocationHandler) {
        return new FeignProxyFactory(feignInvocationHandler);
    }

}

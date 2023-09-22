package com.kuafoo4j.example.test;

import com.kuafoo4j.example.feign.OrderFeignClient;
import com.kuafoo4j.feign.core.proxy.FeignInvocationHandler;
import com.kuafoo4j.feign.core.proxy.FeignProxyFactory;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-22 11:22
 */
public class TestController {
    public static void main(String[] args) {
        OrderFeignClient orderFeignClient = new OrderFeignClient() {
            @Override
            public String getServerInfo() {
                return null;
            }
        };

        FeignProxyFactory feignProxyFactory = new FeignProxyFactory(new FeignInvocationHandler(orderFeignClient));

        Object feignProxy = feignProxyFactory.getFeignProxy(orderFeignClient);

        System.out.println(feignProxy);
    }

}

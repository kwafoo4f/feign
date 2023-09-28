package com.kuafoo4j.example.test;

import com.kuafoo4j.example.feign.OrderFeignClient;
import com.kuafoo4j.feign.core.proxy.FeignProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-22 11:22
 */
@RestController
@RequestMapping("/feign/test")
public class TestController {
    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping
    public String feignTest() {
        return orderFeignClient.getServerInfo();
    }

}

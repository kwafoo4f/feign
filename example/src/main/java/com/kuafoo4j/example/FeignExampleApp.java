package com.kuafoo4j.example;

import com.kuafoo4j.feign.core.annatation.EnableFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-28 15:38
 */
@EnableFeignClient(basePacket = "com.kuafoo4j.example.feign")
@SpringBootApplication
public class FeignExampleApp {
    public static void main(String[] args) {
        SpringApplication.run(FeignExampleApp.class,args);
    }

}

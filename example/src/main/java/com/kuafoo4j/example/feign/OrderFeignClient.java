package com.kuafoo4j.example.feign;

import com.kuafoo4j.feign.core.annatation.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-22 11:20
 */
@FeignClient(name = "order-service")
public interface OrderFeignClient {
    String PRE_FIX = "/service";

    @GetMapping(PRE_FIX + "/server-info")
    String getServerInfo();

}

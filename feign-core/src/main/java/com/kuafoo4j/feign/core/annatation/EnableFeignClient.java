package com.kuafoo4j.feign.core.annatation;

import java.lang.annotation.*;

/**
 * 开启feign
 * @author zk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableFeignClient {
}

package com.kuafoo4j.feign.core.annatation;

import java.lang.annotation.*;

/**
 * feign客户端
 * @author zk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface FeignClient {
    /**
     * 服务名称
     * @return
     */
    String name() default "";

    /**
     * 主机地址
     * @return
     */
    String url() default "";

}

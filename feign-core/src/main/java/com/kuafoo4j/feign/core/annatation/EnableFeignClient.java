package com.kuafoo4j.feign.core.annatation;

import com.kuafoo4j.feign.core.config.FeignClientRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启feign
 *
 * @author zk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(FeignClientRegistry.class)
public @interface EnableFeignClient {
    /**
     * 包路径
     * @return
     */
    String basePacket() default "";
}

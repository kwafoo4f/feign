package com.kuafoo4j.feign.core.config;

import cn.hutool.extra.spring.SpringUtil;
import com.kuafoo4j.feign.core.annatation.EnableFeignClient;
import com.kuafoo4j.feign.core.annatation.FeignClient;
import com.kuafoo4j.feign.core.proxy.FeignProxyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;

import java.beans.Introspector;
import java.io.File;
import java.net.URL;

/**
 * @description:
 * @author: zk
 * @date: 2023-09-28 17:39
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean
public class FeignClientRegistry implements BeanPostProcessor {
    @Autowired
    private FeignProxyFactory feignProxyFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(EnableFeignClient.class)) {
            scan(bean.getClass());
        }
        return bean;
    }

    /**
     * 扫描bean,封装为beanDefinition
     *
     * @param clazz
     */
    private void scan(Class<?> clazz) {
        // 获取扫描路径
        EnableFeignClient enableFeignClientAnnotation = clazz.getAnnotation(EnableFeignClient.class);
        String packagePath = enableFeignClientAnnotation.basePacket();
        ClassLoader classLoader = clazz.getClassLoader();
        String sysPath = packagePath.replace(".", "//");
        URL url = classLoader.getResource(sysPath);
        // 扫描文件
        File file = new File(url.getFile());
        scanFile(file, classLoader);
    }

    /**
     * 扫描文件,使用类加载器将文件加载为class
     *
     * @param file
     * @param classLoader
     */
    private void scanFile(File file, ClassLoader classLoader) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    // 是文件夹继续进入文件夹扫描
                    scanFile(f, classLoader);
                    continue;
                }

                // 使用类加载器加载文件
                String absolutePath = f.getAbsolutePath();
                String sysClassPath = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
                String classPath = sysClassPath.replace("\\", ".");
                try {
                    Class<?> clazz = classLoader.loadClass(classPath);
                    if (clazz.isAnnotationPresent(FeignClient.class)) {
                        registryFeignClientBean(clazz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 注册FeignClientBean
     *
     * @param clazz
     */
    private void registryFeignClientBean(Class clazz) {
        Object feignClientProxyBean = feignProxyFactory.getFeignProxy(clazz);
        String beanName = Introspector.decapitalize(clazz.getSimpleName());
        log.info("registry feign client proxy bean {} ",beanName);
        SpringUtil.registerBean(beanName, feignClientProxyBean);
    }

}

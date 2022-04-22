package com.example.listener;

import com.alibaba.fastjson.JSON;
import com.example.annotation.AttrDefineAnnotation;
import com.example.annotation.ClassDefineAnnotation;
import com.example.annotation.MethodDefineAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;
import sun.misc.Unsafe;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

/**
 * @author zfl
 * @create 2022/1/27 16:11
 * @description
 */
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final GenericWebApplicationContext resourceLoader;

    public ApplicationReadyListener(GenericWebApplicationContext resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        logger.info("application ready start");
//        logger.info("application context:{}", event.getApplicationContext().getApplicationName());
//        logger.info("application context:{}", JSON.toJSONString(event.getApplicationContext().getEnvironment()));
//        Set<ApplicationListener<?>> listeners = event.getSpringApplication().getListeners();
//        try {
//            resolveResources();
//        }catch (Exception e){
//            logger.error(e.getMessage(),e);
//        }
//        listeners.forEach(this::accept);
    }


    public void resolveResources() throws Exception{
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);

        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(resolver);

        Resource[] resources = resolver.getResources("classpath:com/example/**/*.class");

        for (Resource resource : resources){

            MetadataReader metadataReader = factory.getMetadataReader(resource);

            Class<?> aClass = Class.forName(metadataReader.getClassMetadata().getClassName());

            if(aClass.isAnnotationPresent(ClassDefineAnnotation.class)){
                System.out.println("ClassDefineAnnotation:" + aClass);
            }else if(aClass.isAnnotationPresent(MethodDefineAnnotation.class)){
                System.out.println("MethodDefineAnnotation:" + aClass);
            }else if(aClass.isAnnotationPresent(AttrDefineAnnotation.class)){
                System.out.println("AttrDefineAnnotation:" + aClass);
            }
        }

//        ResourcePatternUtils.getResourcePatternResolver(resourceLoader,, ClassDefineAnnotation.class);

    }

    private void accept(ApplicationListener<?> listener) {
        logger.info(listener.getClass().getName());
    }
}

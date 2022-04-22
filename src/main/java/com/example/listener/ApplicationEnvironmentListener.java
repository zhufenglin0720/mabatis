package com.example.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zfl
 * @create 2022/1/27 16:13
 * @description
 */
@Component
public class ApplicationEnvironmentListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        logger.info("application environment start");
        logger.info("application environment:{}",event.getEnvironment());
    }
}

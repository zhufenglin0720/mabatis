package com.example.listener;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zfl
 * @create 2022/1/27 16:13
 * @description
 */
@Component
public class ApplicationStartListener implements ApplicationListener<ApplicationStartedEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        logger.info("application start");
        logger.info("application start timestamp:{}", event.getTimestamp());
    }
}

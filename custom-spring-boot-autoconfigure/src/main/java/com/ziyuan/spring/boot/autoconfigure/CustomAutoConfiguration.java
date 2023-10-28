package com.ziyuan.spring.boot.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({CustomProperties.class})
public class CustomAutoConfiguration implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CustomAutoConfiguration.class);

    private final CustomAutoConfiguration properties;

    public CustomAutoConfiguration(CustomAutoConfiguration properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("custom properties after properties set");
    }
}

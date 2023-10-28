package com.ziyuan.spring.boot.autoconfigure.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultCustomHandler implements CustomHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCustomHandler.class);

    @Override
    public void handler() {
        logger.info("choose default handler");
    }

}


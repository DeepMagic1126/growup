package org.deepmagic.project.service;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JdkProxyService
 *
 * @author chenbin
 * @since 2025/3/28 15:40
 */
@Service
@Order(0)
public class JdkProxyService implements ProxyService {

    @Resource
    private CglibProxyService cglibProxyService;

    @Override
    @Transactional
    @Async
    public void run() {

    }
}

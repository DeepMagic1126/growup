package org.deepmagic.project.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CglibProxyService
 *
 * @author chenbin
 * @since 2025/3/28 15:40
 */

@Service
public class CglibProxyService {

    @Resource
    private JdkProxyService jdkProxyService;

    @Transactional
    @Async
    public void run() {

    }
}

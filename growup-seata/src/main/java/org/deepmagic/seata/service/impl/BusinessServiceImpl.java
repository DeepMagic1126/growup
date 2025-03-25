package org.deepmagic.seata.service.impl;

import jakarta.annotation.Resource;
import org.apache.seata.core.context.RootContext;
import org.apache.seata.spring.annotation.GlobalTransactional;
import org.deepmagic.seata.service.BusinessService;
import org.deepmagic.seata.service.OrderService;
import org.deepmagic.seata.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * OrderServiceImpl
 *
 * @author chenbin
 * @since 2025/3/25 16:09
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessService.class);

    @Resource
    private StorageService storageService;
    @Resource
    private OrderService orderService;

    private final Random random = new Random();

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "spring-seata-tx")
    public void purchase(String userId, String commodityCode, int orderCount) {
        LOGGER.info("purchase begin ... xid: " + RootContext.getXID());
        storageService.deduct(commodityCode, orderCount);
        orderService.create(userId, commodityCode, orderCount);
        if (random.nextBoolean()) {
            throw new RuntimeException("random exception mock!");
        }
    }
}

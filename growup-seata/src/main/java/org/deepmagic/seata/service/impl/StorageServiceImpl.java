package org.deepmagic.seata.service.impl;

import jakarta.annotation.Resource;
import org.apache.seata.core.context.RootContext;
import org.deepmagic.seata.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * OrderServiceImpl
 *
 * @author chenbin
 * @apiNote
 * @since 2025/3/25 16:09
 */
@Service
public class StorageServiceImpl implements StorageService {


    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void deduct(String commodityCode, int count) {
        LOGGER.info("Stock Service Begin ... xid: " + RootContext.getXID());
        LOGGER.info("Deducting inventory SQL: update stock_tbl set count = count - {} where commodity_code = {}", count,
                commodityCode);

        jdbcTemplate.update("update stock_tbl set count = count - ? where commodity_code = ?",
                count, commodityCode);
        LOGGER.info("Stock Service End ... ");

    }
}

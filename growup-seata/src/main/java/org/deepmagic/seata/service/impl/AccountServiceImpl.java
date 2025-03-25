package org.deepmagic.seata.service.impl;

import jakarta.annotation.Resource;
import org.apache.seata.core.context.RootContext;
import org.deepmagic.seata.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * AccountServiceImpl
 *
 * @author chenbin
 * @since 2025/3/25 16:06
 */
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void debit(String userId, int money) {
        LOGGER.info("Account Service ... xid: " + RootContext.getXID());
        LOGGER.info("Deducting balance SQL: update account_tbl set money = money - {} where user_id = {}", money,
                userId);

        jdbcTemplate.update("update account_tbl set money = money - ? where user_id = ?", money, userId);
        LOGGER.info("Account Service End ... ");
    }
}

package org.deepmagic.seata.service;

/**
 * AccountService
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/25 16:03
 */
public interface AccountService {

    /**
     * 余额扣款
     *
     * @param userId 用户ID
     * @param money  扣款金额
     */
    void debit(String userId, int money);

}

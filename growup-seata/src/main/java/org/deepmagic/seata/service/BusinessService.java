package org.deepmagic.seata.service;

/**
 * BusinessService
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/25 16:03
 */
public interface BusinessService {

    /**
     * 用户订购商品
     *
     * @param userId        用户ID
     * @param commodityCode 商品编号
     * @param orderCount    订购数量
     */
    void purchase(String userId, String commodityCode, int orderCount);

}

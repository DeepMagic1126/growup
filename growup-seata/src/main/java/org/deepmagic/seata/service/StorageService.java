package org.deepmagic.seata.service;

/**
 * StorageService
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/25 16:05
 */
public interface StorageService {

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编号
     * @param count         扣减数量
     */
    void deduct(String commodityCode, int count);

}

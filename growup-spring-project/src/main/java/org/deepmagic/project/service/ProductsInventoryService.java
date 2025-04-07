package org.deepmagic.project.service;

//import com.baomidou.dynamic.datasource.annotation.DS;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.deepmagic.project.entity.ProductsInventory;
import org.deepmagic.project.mapper.ProductsInventoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * ProductsInventoryService
 *
 * @author chenbin
 * @since 2025/3/7 22:17
 */
@Service
public class ProductsInventoryService {

    @Resource
    private ProductsInventoryMapper mapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;

//    @DS("master")
    public ProductsInventory get(Long productId) {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        return mapper.get(productId);
    }

//    @DS("slave")
    public ProductsInventory slaveGet(Long productId) {
        return mapper.get(productId);
    }


//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String buy(Long productId) {
        int i = mapper.buy(productId);
        if (i < 1) {
            System.out.println("库存不足");
            return "库存不足";
        }
        try {
            TimeUnit.of(ChronoUnit.MILLIS).sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("购买成功");
        return "购买成功";
    }
}

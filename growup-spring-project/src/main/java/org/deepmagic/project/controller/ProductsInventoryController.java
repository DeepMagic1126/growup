package org.deepmagic.project.controller;

import jakarta.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.deepmagic.project.entity.ProductsInventory;
import org.deepmagic.project.service.CglibProxyService;
import org.deepmagic.project.service.JdkProxyService;
import org.deepmagic.project.service.ProductsInventoryService;
import org.deepmagic.project.service.ProductsInventorySolveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author chenbin
 * @apiNote controller
 * @since 2025/3/7 22:15
 */
@RestController
@RequestMapping(("/products_inventory"))
public class ProductsInventoryController {

    @Resource
    private ProductsInventoryService productsInventoryService;
    @Resource
    private ProductsInventorySolveService productsInventorySolveService;

    @Resource
    private JdkProxyService jdkProxyService;
    @Resource
    private CglibProxyService cglibProxyService;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @GetMapping("/get/{productId}")
    private ProductsInventory get(@PathVariable Long productId) {
        return productsInventoryService.get(productId);
    }

    @GetMapping("/slave/get/{productId}")
    private ProductsInventory slaveGet(@PathVariable Long productId) {
        return productsInventoryService.slaveGet(productId);
    }


    @GetMapping("/buy/{productId}")
    private String buy(@PathVariable Long productId) {
        return productsInventoryService.buy(productId);
    }

    /**
     * redis解决方式 缓存库存
     */
    @GetMapping("/buy/type1/{productId}")
    private String buy1(@PathVariable Long productId) {
        return productsInventorySolveService.buy1(productId);
    }

    /**
     * redis解决方式 lua在redis扣库存，数据库执行同步
     */
    @GetMapping("/buy/type2/{productId}")
    private String buy2(@PathVariable Long productId) {
        return productsInventorySolveService.buy2(productId);
    }

    /**
     * redis解决方式 lua在redis扣库存，数据库执行异步
     */
    @GetMapping("/buy/type3/{productId}")
    private String buy3(@PathVariable Long productId) {
        return productsInventorySolveService.buy3(productId);
    }

}

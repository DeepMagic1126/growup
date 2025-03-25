package org.deepmagic.openfeign.service;

import org.deepmagic.openfeign.feign.OrderFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProdcutService {

    @Autowired
    private OrderFeignService orderFeignService;

    public void generateOrder() {
        String orderById = orderFeignService.findOrderById(100L);
        System.out.println("test");
    }


}

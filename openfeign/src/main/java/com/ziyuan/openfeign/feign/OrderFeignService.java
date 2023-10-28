package com.ziyuan.openfeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "mall-order", path = "/test")
public interface OrderFeignService {

    @RequestMapping("/order/{orderId}")
    String findOrderById(@PathVariable("orderId") Long orderId);
}

package org.deepmagic.project.service;

import jakarta.annotation.Resource;
import org.deepmagic.project.entity.ProductsInventory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ProductsInventorySolveService
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/8 14:38
 */
@Service
public class ProductsInventorySolveService {


    @Resource
    private ProductsInventoryService productsInventoryService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private Executor executor = Executors.newFixedThreadPool(8);

    private static final String script = """
            if redis.call("EXISTS", KEYS[1]) == 0 then
                return nil
            end
            local c = redis.call("GET",KEYS[1])
            local c_number = tonumber(c)
            if c_number < 1 then
                return "0"
            end
            local new_c = tostring(c_number-1)
            redis.call("SET",KEYS[1],new_c)
            return new_c
            """;
    public String buy1(Long productId) {

        String count = stringRedisTemplate.opsForValue().get("ProductsInventory:" + productId);

        if (count == null) {
            ProductsInventory productsInventory = productsInventoryService.get(productId);
            Long quantity = productsInventory.getQuantity();
            if (quantity <= 0) {
                stringRedisTemplate.opsForValue().set("ProductsInventory:" + productId, quantity + "",10, TimeUnit.SECONDS);
                System.out.println("数量不足【0】");
                return "数量不足[0]";
            }
        }

        if (count != null && Long.parseLong(count) <= 0L) {
            System.out.println("数量不足【1】");
            return "数量不足[1]";
        }
        String buy = productsInventoryService.buy(productId);
        ProductsInventory productsInventory = productsInventoryService.get(productId);
        Long quantity = productsInventory.getQuantity();

        stringRedisTemplate.opsForValue().set("ProductsInventory:" + productId, quantity + "",10, TimeUnit.SECONDS);

        return buy;
    }

    public String buy2(Long productId){
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptText(script);
        String result;
        try {
            result = stringRedisTemplate.opsForValue().getOperations().execute(redisScript
                    , List.of("ProductsInventory:" + productId));
        } catch (Exception e) {
            // 防止由于网络波动导致的红包异常 暂时做通知处理，如果情况比较多，后续进行补偿
            throw e;
        }

        if (result == null) {
            ProductsInventory productsInventory = productsInventoryService.get(productId);
            Long quantity = productsInventory.getQuantity();
            stringRedisTemplate.opsForValue().set("ProductsInventory:" + productId, quantity + "",60, TimeUnit.SECONDS);
            if (quantity <= 0) {
                System.out.println("数量不足【0】");
                return "数量不足[0]";
            }
        }
        if (result != null && Long.parseLong(result) <= 0L) {
            System.out.println("数量不足【1】");
            return "数量不足[1]";
        }

        return productsInventoryService.buy(productId);
    }


    public String buy3(Long productId){
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(String.class);
        redisScript.setScriptText(script);
        String result;
        try {
            result = stringRedisTemplate.opsForValue().getOperations().execute(redisScript
                    , List.of("ProductsInventory:" + productId));
        } catch (Exception e) {
            // 防止由于网络波动导致的红包异常 暂时做通知处理，如果情况比较多，后续进行补偿
            throw e;
        }

        if (result == null) {
            ProductsInventory productsInventory = productsInventoryService.get(productId);
            Long quantity = productsInventory.getQuantity();
            stringRedisTemplate.opsForValue().set("ProductsInventory:" + productId, quantity + "",60, TimeUnit.SECONDS);
            if (quantity <= 0) {
                System.out.println("数量不足【0】");
                return "数量不足[0]";
            }
        }
        if (result != null && Long.parseLong(result) <= 0L) {
            System.out.println("数量不足【1】");
            return "数量不足[1]";
        }

        executor.execute(()-> {
            productsInventoryService.buy(productId);
        });

        return "购买成功";
    }
}

package com.meek.shopmall;

import com.meek.shopmall.goods.ShopGoods;
import com.meek.shopmall.goods.ShopGoodsMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@ServletComponentScan
@EnableScheduling
@ConfigurationPropertiesScan
public class GoodsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GoodsApplication.class, args);


        ShopGoodsMapper bean = applicationContext.getBean(ShopGoodsMapper.class);
        ShopGoods shopGoods = new ShopGoods();
        shopGoods.setName("Iphone15");
        shopGoods.setId(100000);
        shopGoods.setAmount(1000);
        bean.insert(shopGoods);
    }
}

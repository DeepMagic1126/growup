package shopmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import shopmall.goods.ShopOrders;
import shopmall.goods.ShopOrdersMapper;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@ServletComponentScan
@EnableScheduling
@ConfigurationPropertiesScan
public class OrdersApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrdersApplication.class, args);


        ShopOrdersMapper bean = applicationContext.getBean(ShopOrdersMapper.class);
        ShopOrders orders = new ShopOrders();
        orders.setGid(100000);
        orders.setId(1);
        orders.setPurchaseAmount(1);
        bean.insert(orders);
    }
}

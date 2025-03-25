package org.deepmagci.naocs.client;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;

import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Client
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/12 22:49
 */
public class Client {

    public static void main(String[] args) throws NacosException, ExecutionException, InterruptedException {
        String serverAddr = "localhost:8848";

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, "localhost:8848");
        properties.setProperty(PropertyKeyConst.NAMESPACE, "default");
        ConfigService configService = NacosFactory.createConfigService(properties);

        NamingService namingService = NacosFactory.createNamingService(properties);

        CompletableFuture<Boolean> wait = new CompletableFuture<>();

        wait.get();
    }
}

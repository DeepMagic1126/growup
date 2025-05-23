package org.deepmagic.project.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * RedisConfig
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/8 14:36
 */
@Configuration
public class RedisConfig {

    @Resource
    RedisConnectionFactory connectionFactory;

    @Bean
//    @Transactional
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;
    }

}

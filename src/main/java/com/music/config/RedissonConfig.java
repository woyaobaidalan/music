package com.music.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();

        config.useSingleServer().setAddress("redis://192.168.41.100:6379").setPassword("lhg5665655+");

        return Redisson.create(config);
    }

}

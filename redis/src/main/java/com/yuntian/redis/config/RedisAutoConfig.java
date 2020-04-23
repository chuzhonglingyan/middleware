package com.yuntian.redis.config;


import com.alibaba.fastjson.parser.ParserConfig;
import com.yuntian.redis.util.RedisClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author guangleilei.
 * @date Created in 16:00 2019/11/13
 * @description
 */
@Configuration
public class RedisAutoConfig {


    /**
     * 初始化Redis
     *
     * @return
     */
    @Bean
    public  RedisClient redisCache(RedisTemplate redisTemplate) {
        RedisClient redisCache = new RedisClient();
        redisCache.setRedisTemplate(redisTemplate);
        return redisCache;
    }


    @Bean("redisTemplate")
    public <V> RedisTemplate getRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }

    @Bean
    public RedisSerializer keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public <V> RedisSerializer<V> valueSerializer() {
        FastJsonRedisSerializer<V> fastJsonRedisSerializer = new FastJsonRedisSerializer<>();
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return fastJsonRedisSerializer;
    }


}

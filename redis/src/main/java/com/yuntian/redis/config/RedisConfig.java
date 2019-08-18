package com.yuntian.redis.config;

import com.alibaba.fastjson.parser.ParserConfig;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/17 0017 22:14
 * @Description:
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {

    @Bean
    public <V> RedisTemplate<String, V> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer<V> fastJsonRedisSerializer = new FastJsonRedisSerializer<>();
        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    RedisManage redisManage(){
        return  new RedisManage();
    }

}

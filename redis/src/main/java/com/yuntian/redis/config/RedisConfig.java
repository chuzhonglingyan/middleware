package com.yuntian.redis.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.yuntian.redis.lock.RedissLockUtil;
import com.yuntian.redis.lock.RedissonDistributedLocker;

import org.redisson.api.RedissonClient;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/8/17 0017 22:14
 * @Description:
 */
@Slf4j
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean(name = "redisTemplate")
    public <V> RedisTemplate<String, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
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
    RedisManage redisManage() {
        return new RedisManage();
    }


    @Bean
    RedissonDistributedLocker redissonDistributedLocker(RedissonClient redissonClient) {
        RedissonDistributedLocker redissonDistributedLocker = new RedissonDistributedLocker();
        redissonDistributedLocker.setRedissonClient(redissonClient);
        RedissLockUtil.setLocker(redissonDistributedLocker);
        return redissonDistributedLocker;
    }


    @Override
    @Bean
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }

            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }

            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }

            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }


}

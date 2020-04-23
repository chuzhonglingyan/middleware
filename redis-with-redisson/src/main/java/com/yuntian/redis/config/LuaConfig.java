package com.yuntian.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/11/21 0021 21:30
 * @Description:
 */
@Configuration
public class LuaConfig {

    @Bean(name = "ratelimitingRedisScript")
    public DefaultRedisScript<Boolean> ratelimitingRedisScript() {
        DefaultRedisScript<Boolean> ratelimitingRedisScript = new DefaultRedisScript<>();
        ratelimitingRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/ratelimiting-client.lua")));
        ratelimitingRedisScript.setResultType(Boolean.class);
        return ratelimitingRedisScript;
    }

    @Bean(name = "redisScriptTest")
    public DefaultRedisScript<Boolean> redisScriptTest() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/Test.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }


}

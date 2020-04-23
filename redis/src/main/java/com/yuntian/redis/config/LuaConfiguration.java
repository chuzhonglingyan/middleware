package com.yuntian.redis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {


    @Bean("redisLockScript")
    public DefaultRedisScript<Boolean> redisLockScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/unLock.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }

    @Bean("redisLimitScript")
    public DefaultRedisScript<Boolean> redisLimitScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/ratelimiting-client.lua")));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}


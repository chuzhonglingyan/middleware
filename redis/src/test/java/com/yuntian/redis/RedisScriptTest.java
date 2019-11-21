package com.yuntian.redis;

import com.yuntian.redis.service.RedisLuaService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.StaticScriptSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @Auther: yuntian
 * @Date: 2019/11/21 0021 21:15
 * @Description: https://segmentfault.com/a/1190000019676878
 * https://yq.aliyun.com/articles/706121
 */
@SpringBootTest
public class RedisScriptTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private DefaultRedisScript<Integer> ratelimitingRedisScript;

    @Resource
    private DefaultRedisScript<Boolean> redisScriptTest;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void testScript1() {
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptSource(new StaticScriptSource("redis.call('SET', KEYS[1], ARGV[1]); return ARGV[1]"));
        script.setResultType(String.class);
        Object result = redisTemplate.execute(script, Collections.singletonList("test_key1"), Collections.singletonList("hhhh"));
        System.out.println(result);
    }


    @Test
    public void testScript21() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new StaticScriptSource("local times = redis.call('incr',KEYS[1]) local expire = ARGV[1] " +
                "if times > tonumber(ARGV[1]) then return 0 end return 1"));
        List<String> keys = Arrays.asList("test-ip");
        Long result = stringRedisTemplate.execute(redisScript, keys, 20);
        System.out.println(result);
    }

    @Test
    public void testScript2() {
        //redis-cli --eval ratelimiting-client.lua rate.limitingl:127.0.0.1 , 10 3
        //结合脚本的内容可知这行命令的作用是将访问频率限制为每10秒最多3次
        Map<String, Object> argvMap = new HashMap<>();
        argvMap.put("expire", 10);
        argvMap.put("count", 3);
        Object result = redisTemplate.execute(ratelimitingRedisScript, Collections.singletonList("rate.limitingl:127.0.0.1"),argvMap);
        System.out.println(result);
    }

    @Test
    public void testRedisScriptTest() {
        // 限制一定时间内只能插入一次
        List<String> keys = Arrays.asList("testLua", "hello lua");
        Boolean execute = stringRedisTemplate.execute(redisScriptTest, keys, "100");
        System.out.println(execute);
    }

    @Resource
    private RedisLuaService redisLuaService;

    @Test
    public void testScript55() {
        for (int i = 0; i < 30; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(redisLuaService.rateLimit("127.0.0.1",3,10));
        }
    }
}

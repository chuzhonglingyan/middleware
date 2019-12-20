package com.yuntian.redis.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Auther: yuntian
 * @Date: 2019/12/20 0020 21:06
 * @Description:
 */
public class MyStringSerializer  implements RedisSerializer<String> {


    private final Charset charset;

    /**
     * Creates a new {@link StringRedisSerializer} using {@link StandardCharsets#UTF_8 UTF-8}.
     */
    public MyStringSerializer() {
        this.charset=StandardCharsets.UTF_8;
    }


    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(@Nullable String string) {
        return (string == null ? null : string.getBytes(charset));
    }

    @Override
    public Class<?> getTargetType() {
        return String.class;
    }

}

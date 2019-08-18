package com.yuntian.redis.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yuntian.redis.util.GenericsUtils;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Administrator
 * @Auther: yuntian
 * @Date: 2019/2/16 0016 14:53
 * @Description:
 */
public class FastJsonRedisSerializer<V> implements RedisSerializer<V> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Class<V> clazz;

    public FastJsonRedisSerializer() {
        super();
        this.clazz = GenericsUtils.getSuperClassGenricType(getClass());
    }


    @Override
    public byte[] serialize(V t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    public V deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return JSON.parseObject(str, clazz);
    }

}

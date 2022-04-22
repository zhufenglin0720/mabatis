package com.example.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.charset.Charset;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * The type Fast json 2 json redis serializer.
 *
 * @author 168
 * @param <T> the type parameter
 */
public class FastJson2JsonRedisSerializer<T> implements RedisSerializer<T> {

    /**
     * The constant DEFAULT_CHARSET.
     */
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private Class<T> clazz;

    /**
     * Instantiates a new Fast json 2 json redis serializer.
     */
    FastJson2JsonRedisSerializer() {
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    /**
     * Instantiates a new Fast json 2 json redis serializer.
     *
     * @param clazz the clazz
     */
    FastJson2JsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        String str = new String(bytes, DEFAULT_CHARSET);
        if (clazz == null) {
            return (T) JSON.parse(str);
        }
        return (T) JSON.parseObject(str, clazz);
    }

}


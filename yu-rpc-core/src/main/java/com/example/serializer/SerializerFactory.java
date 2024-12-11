package com.example.serializer;


import com.example.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {



    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 序列化映射（用于实现单例）
     */
    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = new HashMap<String, Serializer>() {{

        SpiLoader.getInstance().load(Serializer.class);

    }};

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get("jdk");

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        //默认的实例构造器
        SpiLoader instance = SpiLoader.getInstance();
        instance.load(Serializer.class);

        return instance.load(Serializer.class, key);
    }

}

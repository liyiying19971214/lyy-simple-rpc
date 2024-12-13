package com.example.serializer;


import com.example.spi.SpiLoader;

/**
 * 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {


    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        //默认的实例构造器
        SpiLoader instance = SpiLoader.getInstance();
        return  instance.initSerializer(Serializer.class,key);
    }

}

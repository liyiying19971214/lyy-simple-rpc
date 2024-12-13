package com.example.registry;

import com.example.serializer.Serializer;
import com.example.spi.SpiLoader;

public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }


    public static Registry  getInstance(String key) {
        //默认的实例构造器
        SpiLoader instance = SpiLoader.getInstance();
        return  instance.initSerializer(Registry.class,key);
    }
}

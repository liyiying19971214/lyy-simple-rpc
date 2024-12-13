package com.example.spi;


import cn.hutool.core.io.resource.ResourceUtil;
import com.example.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SpiLoader {

    private  volatile  static   SpiLoader instance =null;


    //spi的系统路径
    private static final String RPC_SYSTEM_SPI_DIR= "META-INF/rpc/system/";


    //spi的自定义用户路径
    private static final String RPC_CUSTOM_SPI_DIR = "META-INF/rpc/custom/";


    //路径内容
    private static final String[] SCAN_DIRS = new  String[]{RPC_SYSTEM_SPI_DIR,RPC_CUSTOM_SPI_DIR};


    private   static  Map<String, Map<String,Class<?>>> loaderMap=new HashMap<>();


    static Map<String,Object>  cacheMap=new ConcurrentHashMap<>();





    // 提供公共方法获取单例
    public  static   SpiLoader  getInstance() {
        if(instance==null){
            synchronized (SpiLoader.class){
                // 第一次调用时，才进行懒加载初始化
                if (instance == null) {
                    instance = new SpiLoader();

                }
            }
        }
        return instance;
    }


    /**
     * 实例化内容
     * @param tclass
     * @param key
     * @return
     * @param <T>
     */
    public  <T>T initSerializer(Class<?> tclass, String key){
        String name = tclass.getName();
        Map<String, Class<?>> keyclassMap = loaderMap.get(name);
        if(keyclassMap==null){
            throw  new RuntimeException(String.format("未加载类型"));
        }
        if(!keyclassMap.containsKey(key)){
            throw new RuntimeException(String.format("类型不存在"));
        }
        Class<?> aClass = keyclassMap.get(key);
        String aClassName = aClass.getName();

        //丢入缓存防止重复加载

        if(!cacheMap.containsKey(aClassName)){
            try {
                cacheMap.put(aClassName,aClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            
        }
            return (T) cacheMap.get(aClassName);
    }




    /**
     * 加载spi内容
     *
     * @return
     */
    public  static Map<String, Map<String, Class<?>>> load(Class<?> loadClass) {
        Map<String, Class<?>> calssMap = new HashMap<>();
        for (String scanDir : SCAN_DIRS)
            {
                List<URL> resources = ResourceUtil.getResources(scanDir + loadClass.getName());
                for (URL resource : resources) {
                    //加载文件内容
                    try {
                        InputStreamReader inputStreamReader = new InputStreamReader(resource.openStream());
                        //转化成为字符流
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        //读取配置文件
                        String link;
                        while ((link = bufferedReader.readLine()) != null) {
                            String[] split = link.split("=");
                            String key = split[0];
                            String className = split[1];
                            //放入到数据中去
                            calssMap.put(key, Class.forName(className));
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }

                }
            }
        loaderMap.put(loadClass.getName(), calssMap);
        return loaderMap;
    }
}

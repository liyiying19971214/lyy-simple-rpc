package com.example.registry;

import com.example.config.RegistryConfig;
import com.example.model.ServiceMetaInfo;

import java.util.List;

public interface Registry {

    void  init(RegistryConfig registryConfig);

    void  registry(ServiceMetaInfo serviceMetaInfo) throws Exception;

    void  unRegistry(ServiceMetaInfo serviceMetaInfo);

    //销毁
    void  destory(RegistryConfig registryConfig);


     List<ServiceMetaInfo> serviceDiscovery(String serviceKey);


     void  heartBeat();
}

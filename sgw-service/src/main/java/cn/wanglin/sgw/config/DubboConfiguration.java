package cn.wanglin.sgw.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@DubboComponentScan(basePackages = "cn.wanglin.sgw")
public class DubboConfiguration {
    private String appName = "sgw-server";
    @Value("${dubbo.registry.address}")
    private String address;

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setName(appName);
        applicationConfig.setName(appName);
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(address);
//        registryConfig.setClient("curator");
        registryConfig.setGroup("dubbo");
        registryConfig.setPort(-1);
        return registryConfig;
    }


    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setPort(-1);
        config.setSerialization("fastjson");
        return config;
    }

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
//        providerConfig.setLoadbalance("consumesameorgfirst");
        return providerConfig;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig config = new ConsumerConfig();
//        config.setCluster("purefailover");
        return config;
    }

}

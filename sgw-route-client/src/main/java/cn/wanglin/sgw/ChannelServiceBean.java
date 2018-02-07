package cn.wanglin.sgw;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ChannelServiceBean<T extends ChannelService> implements   FactoryBean<T> {
    ClassLoader classLoader;
    Class<T> clz;

    public ChannelServiceBean(Class<T> clz,ClassLoader classLoader) {
        this.clz = clz;
        this.classLoader = classLoader;
    }


    @Override
    public T getObject() throws Exception {
        // Create proxy
        Object obj = Proxy.newProxyInstance(classLoader, new Class[]{clz, ChannelService.class}, new SimpleChannelService(clz));
        return (T) obj;
    }

    @Override
    public Class<?> getObjectType() {
        return clz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

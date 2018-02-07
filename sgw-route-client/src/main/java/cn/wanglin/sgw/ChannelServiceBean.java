package cn.wanglin.sgw;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
import org.springframework.beans.factory.FactoryBean;

public class ChannelServiceBean<T extends ChannelService> implements   FactoryBean<T> {
    ClassLoader classLoader;
    Class<T> clz;

    public ChannelServiceBean(Class<T> clz,ClassLoader classLoader) {
        this.clz = clz;
        this.classLoader = classLoader;
    }


    @Override
    public T getObject() throws Exception {
        ChannelService target = new SimpleChannelService();
        // Create proxy
        ProxyFactory result = new ProxyFactory();
        result.setTarget(target);
        result.setInterfaces(new Class[]{clz, ChannelService.class});

        result.addAdvisor(ExposeInvocationInterceptor.ADVISOR);


        return (T) result.getProxy(classLoader);
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

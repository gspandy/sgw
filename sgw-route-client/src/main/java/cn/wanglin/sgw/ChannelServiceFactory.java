package cn.wanglin.sgw;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ChannelServiceFactory <T extends ChannelService> implements InitializingBean,FactoryBean<T> {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

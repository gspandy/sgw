package cn.wanglin.sgw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SimpleChannelService implements ChannelService, InvocationHandler {
    Logger logger = LoggerFactory.getLogger(getClass());
    Class domainChannelServiceClass;


    public SimpleChannelService(Class domainChannelServiceClass) {
        this.domainChannelServiceClass = domainChannelServiceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Domain da=  (Domain)domainChannelServiceClass.getAnnotation(Domain.class);
        if(null == da || "".equals(da.value())) {
            throw new IllegalArgumentException(domainChannelServiceClass.getSimpleName() + "未配置领域");
        }
        String methodName = method.getName();
        logger.info("执行领域[{}]方法[{}]，参数:{}",da.value(),methodName,args.toString());

        return null;
    }
}

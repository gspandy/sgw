package cn.wanglin.sgw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Map;

public class ExportApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, ChannelConfig> ccs = event.getApplicationContext().getBeansOfType(ChannelConfig.class);
        ccs.forEach((beanname, channelConfig) -> {
            if (channelConfig.endpoint == Endpoint.SERVER) {
                logger.info("发布服务:[{}]", channelConfig.url);
                export(channelConfig);
            }
        });
    }

    private void export(ChannelConfig channelConfig) {
        //todo 发布服务
//        1,http 是通过一个dispatcher servlet，匹配servlet的 url 和 parser之间的关系
//        socket协议的暂时没有
    }

}

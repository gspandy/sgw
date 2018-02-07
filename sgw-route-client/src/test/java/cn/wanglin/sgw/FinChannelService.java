package cn.wanglin.sgw;

import org.springframework.scheduling.annotation.Async;

/**
 * FinChannelService 业务接口
 * ChannelService    网关接口
 */
@Domain("fin")
public interface FinChannelService extends ChannelService {
//    @Sync

    /**
     * 返回值为void的，默认是异步。结果通过回调规范去解决
     *
     * 异步的话，请求能落地到sgw中间件即可。sgw会反复retry，失败通过notify通知。
     * @param out
     * @param in
     * @param amout
     */
    void transfer(String out,String in ,Integer amout);

    /**
     * 返回值非Void 默认是同步。可以通过Sync需定义超时时间
     * @param orderNo
     * @return
     */
    @Sync(60000)
    Object query(String orderNo);
}

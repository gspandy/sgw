package cn.wanglin.sgw;


import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException;
import cn.wanglin.sgw.exception.TimeoutException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 金融交换
 * 功能：构建协议、转化、解析请求返回，处理同异步协议
 *      & 最关键的，资源的维护（线程池资源）
 *      现在是即用即取的。将来如果性能需要，或者协议需要，可能会改成池塘的
 *
 */
public class Channel<REQ, RSP> {
    public  ChannelConfig config;
    private Assembly<REQ> assembly;
    private Parser<RSP>   parser;


    ConcurrentHashMap<String, CountDownLatch> threadBarriers = new ConcurrentHashMap<String, CountDownLatch>();
    ProtocolFactory protocolFactory;
    RSP                            channelResult;

    public Channel(ChannelConfig channelConfig, Assembly assembly, Parser parser) {
        this.config = channelConfig;
        this.assembly = assembly;
        this.parser = parser;
    }


    public Channel send(String channelSequenceId, Map<String, Object> channelRequest) throws TimeoutException, IOException {
        //获取渠道属性
        assembly.setChannelConfig(config);
        parser.setChannelConfig(config);
        //获取连接
        Protocol<REQ,RSP> protocol = protocolFactory.createConnection(config.protocol, this);
        //发送
        protocol.send(channelSequenceId, assembly.render(channelRequest));
        return this;
    }

    /**
     * 获取返回结果
     * 这个方法会被网关的sync方法调用。
     * 如果交换服务是异步的。则同步线程阻塞，等待异步回调。
     * <p>
     * 如果交换服务是同步的。返回结果。
     *
     * @return
     * @throws TimeoutException
     * @throws InterruptedException 发送异常。无论是TimeoutException还是InterruptedException不代表发送失败。建议通过业务结果去检查发送情况。
     */
    SGWResponse getResult(String channelSequenceId) throws TimeoutException, IOException, SignatureException, ParseException, ServerException {
        if (!config.sync) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            try {
                threadBarriers.put(channelSequenceId, countDownLatch);
                if (countDownLatch.await(config.timeout, TimeUnit.MILLISECONDS)) {
                    return parser.parse(channelResult);
                } else {
                    throw new TimeoutException(String.format("交换代码[%s]流水[%s]超时", config.channelCode, channelSequenceId));
                }
            } catch (TimeoutException e) {
                throw e;
            } catch (Exception e) {
                throw new IOException(e);
            } finally {
                threadBarriers.remove(channelSequenceId);
            }
        } else {
            return parser.parse(channelResult);
        }
    }



    /**
     * @param channelSequenceId
     * @param channelResponse
     */
    public void setChannelResponse(String channelSequenceId, RSP channelResponse) {
        CountDownLatch threadBarrier = threadBarriers.get(channelSequenceId);
        if (!config.sync && null == threadBarrier) {
            //LOG TODO
            return;
        }

        this.channelResult = channelResponse;
        if (!config.sync) {
            threadBarrier.countDown();
        }

    }

    public ChannelConfig getConfig() {
        return config;
    }

    public Assembly<REQ> getAssembly() {
        return assembly;
    }

    public Parser<RSP> getParser() {
        return parser;
    }
}

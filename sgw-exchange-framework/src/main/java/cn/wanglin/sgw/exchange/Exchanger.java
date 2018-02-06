package cn.wanglin.sgw.exchange;


import cn.wanglin.sgw.exchange.exception.ParseException;
import cn.wanglin.sgw.exchange.exception.ServerException;
import cn.wanglin.sgw.exchange.exception.SignatureException;
import cn.wanglin.sgw.exchange.exception.TimeoutException;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 金融交换
 * 功能：构建协议、转化、解析请求返回，处理同异步协议
 * & 最关键的，资源的维护（线程池资源）
 * 现在是即用即取的。将来如果性能需要，或者协议需要，可能会改成池塘的
 */
public class Exchanger<REQ, RSP> {
    public  ExchangerConfig config;
    private Assembly<REQ>   assembly;
    private Parser<RSP>     parser;


    ConcurrentHashMap<String, CountDownLatch> threadBarriers = new ConcurrentHashMap<String, CountDownLatch>();
    ProtocolFactory protocolFactory;
    RSP             exchangerResult;

    public Exchanger(ExchangerConfig exchangerConfig, Assembly assembly, Parser parser) {
        this.config = exchangerConfig;
        this.assembly = assembly;
        this.parser = parser;
    }


    public Exchanger send(String exchangerSequenceId, Map<String, Object> exchangerRequest) throws TimeoutException, IOException {
        //获取渠道属性
        assembly.setExchangerConfig(config);
        parser.setExchangerConfig(config);
        //获取连接
        Protocol<REQ, RSP> protocol = protocolFactory.createConnection(config.protocol, this);
        //发送
        protocol.send(exchangerSequenceId, assembly.render(exchangerRequest));
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
    SGWResponse getResult(String exchangerSequenceId) throws TimeoutException, IOException, SignatureException, ParseException, ServerException {
        if (!config.sync) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            try {
                threadBarriers.put(exchangerSequenceId, countDownLatch);
                if (countDownLatch.await(config.timeout, TimeUnit.MILLISECONDS)) {
                    return parser.parse(exchangerResult);
                } else {
                    throw new TimeoutException(String.format("交换代码[%s]流水[%s]超时", config.exchangerCode, exchangerSequenceId));
                }
            } catch (TimeoutException e) {
                throw e;
            } catch (Exception e) {
                throw new IOException(e);
            } finally {
                threadBarriers.remove(exchangerSequenceId);
            }
        } else {
            return parser.parse(exchangerResult);
        }
    }


    /**
     * @param exchangerSequenceId
     * @param exchangerResponse
     */
    public void setExchangerResponse(String exchangerSequenceId, RSP exchangerResponse) {
        CountDownLatch threadBarrier = threadBarriers.get(exchangerSequenceId);
        if (!config.sync && null == threadBarrier) {
            //LOG TODO
            return;
        }

        this.exchangerResult = exchangerResponse;
        if (!config.sync) {
            threadBarrier.countDown();
        }

    }

    public ExchangerConfig getConfig() {
        return config;
    }

    public Assembly<REQ> getAssembly() {
        return assembly;
    }

    public Parser<RSP> getParser() {
        return parser;
    }
}

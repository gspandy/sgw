package cn.wanglin.sgw.exchange

import cn.wanglin.sgw.exchange.zhongan.CreditApplyAssembly
import cn.wanglin.sgw.exchange.zhongan.CreditApplyParser
import cn.wanglin.sgw.exchange.zhongan.ZhongAnProtocol
import com.zhongan.scorpoin.biz.common.CommonRequest
import com.zhongan.scorpoin.biz.common.CommonResponse
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

public class ZhongAnTest {
    Exchanger<CommonRequest, CommonResponse> exchanger = new Exchanger();

    @Before
    public void setup() {
        exchanger.config = new ExchangerConfig(protocol: "zhongan");
        exchanger.assembly = new CreditApplyAssembly();
        exchanger.parser = new CreditApplyParser();
        ProtocolFactory  protocolFactory = mock(ProtocolFactory.class)
        exchanger.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection("zhongan",exchanger)).thenReturn(new ZhongAnProtocol(exchanger: exchanger))
    }

    @Test
    public void test() {
        def send = exchanger.send("1", [
                "a": "aaaa"
        ])
        println "返回报文"+ exchanger.getResult("1");

    }
}
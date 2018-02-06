package cn.wanglin.sgw.exchange

import cn.wanglin.sgw.exchange.lianlian.Bind_Assembly
import cn.wanglin.sgw.exchange.lianlian.Bind_Parser
import cn.wanglin.sgw.exchange.protocol.HttpProtocol
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class LianlianTest {
    Exchanger<String, String> exchanger = new Exchanger(null,null,null);

    @Before
    public void setup() {
        exchanger.config = new ExchangerConfig(protocol: ProtocolType.HTTP,
                reqEntityType: EntityType.JSON,
                url: "https://yintong.com.cn/traderapi/bankcardbind.htm");
        ProtocolFactory protocolFactory = mock(ProtocolFactory.class)
        exchanger.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection(ProtocolType.HTTPS, exchanger)).thenReturn(new HttpProtocol(exchanger: exchanger))


    }


    @Test
    public void bind() {
        exchanger.assembly = new Bind_Assembly();
        exchanger.parser = new Bind_Parser()

        exchanger.send("1", [
                "a": "aaaa"
        ])
        println "返回报文" + exchanger.getResult("1");
    }
}

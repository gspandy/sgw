package cn.wanglin.sgw.exchange

import cn.wanglin.sgw.exchange.haier.T100021_Assembly
import cn.wanglin.sgw.exchange.haier.T100021_Parser
import cn.wanglin.sgw.exchange.protocol.HttpProtocol
import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class HairerTest {
    Exchanger<String, String> exchanger = new Exchanger(null,null,null);

    @Before
    public void setup() {
        exchanger.config = new ExchangerConfig(protocol: ProtocolType.HTTP,
                reqEntityType:EntityType.TEXT,
                url: "http://testpm.haiercash.com/PaymentPlatform/api/HaiercashPayApply",
                attributes: [
                        "privateKey" : "1"
                ]);
        ;
        ProtocolFactory protocolFactory = mock(ProtocolFactory.class)
        exchanger.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection(ProtocolType.HTTP, exchanger)).thenReturn(new HttpProtocol(exchanger: exchanger))


    }


    @Test
    public void V() {
        exchanger.assembly = new T100021_Assembly();
        exchanger.parser = new T100021_Parser()

        exchanger.send("1", [
                serno: "1111"
        ])
        println "返回报文" + JSON.toJSONString(exchanger.getResult("1"));
    }
}

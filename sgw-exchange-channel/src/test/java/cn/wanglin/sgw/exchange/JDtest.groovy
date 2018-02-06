package cn.wanglin.sgw.exchange

import cn.wanglin.sgw.exchange.jd.Q_Assembly
import cn.wanglin.sgw.exchange.jd.Q_Parser
import cn.wanglin.sgw.exchange.jd.V_Assembly
import cn.wanglin.sgw.exchange.jd.V_Parser
import cn.wanglin.sgw.exchange.protocol.HttpProtocol
import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

public class JDtest {
    Exchanger<String, String> exchanger = new Exchanger(null,null,null);

    @Before
    public void setup() {
        exchanger.config = new ExchangerConfig(protocol: ProtocolType.HTTP,
                url: "https://wapi.jd.com/express.htm",
                certPassword: "chinabank",
                certFile: "D:\\project\\simplegateway\\sgw-exchanger\\src\\main\\resources\\quick.keystore",
                attributes: [
                        "VERSION" : ApiConstant.version,
                        "MERCHANT": ApiConstant.merchant,
                        "TERMINAL": ApiConstant.terminal,
                        "des_key" : ApiConstant.des,
                        "md5_key" : ApiConstant.md5
                ]);
        ;
        ProtocolFactory protocolFactory = mock(ProtocolFactory.class)
        exchanger.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection(ProtocolType.HTTP, exchanger)).thenReturn(new HttpProtocol(exchanger: exchanger))


    }


    @Test
    public void V() {
        exchanger.assembly = new V_Assembly();
        exchanger.parser = new V_Parser()

        exchanger.send("1", [
                "a": "aaaa"
        ])
        println "返回报文" + JSON.toJSONString(exchanger.getResult("1"));
    }

    @Test
    public void Q() {
        exchanger.assembly = new Q_Assembly();
        exchanger.parser = new Q_Parser()

        exchanger.send("1", [
                "ID": "1505789430419-asdcfdfgnew"
        ])
        println "返回报文" + JSON.toJSONString(exchanger.getResult("1"));
    }


    @Test
    public void tt() {

    }
}
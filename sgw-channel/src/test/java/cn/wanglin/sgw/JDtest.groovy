package cn.wanglin.sgw

import cn.wanglin.sgw.jd.Q_Assembly
import cn.wanglin.sgw.jd.Q_Parser
import cn.wanglin.sgw.jd.V_Assembly
import cn.wanglin.sgw.jd.V_Parser
import cn.wanglin.sgw.protocol.HttpProtocol
import com.alibaba.fastjson.JSON
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when

public class JDtest {
    Channel<String, String> channel = new Channel(null,null,null);

    @Before
    public void setup() {
        channel.config = new ChannelConfig(protocol: ProtocolType.HTTP,
                url: "https://wapi.jd.com/express.htm",
                certPassword: "chinabank",
                certFile: "D:\\project\\simplegateway\\sgw-channel\\src\\main\\resources\\quick.keystore",
                attributes: [
                        "VERSION" : ApiConstant.version,
                        "MERCHANT": ApiConstant.merchant,
                        "TERMINAL": ApiConstant.terminal,
                        "des_key" : ApiConstant.des,
                        "md5_key" : ApiConstant.md5
                ]);
        ;
        ProtocolFactory protocolFactory = mock(ProtocolFactory.class)
        channel.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection(ProtocolType.HTTP, channel)).thenReturn(new HttpProtocol(channel: channel))


    }


    @Test
    public void V() {
        channel.assembly = new V_Assembly();
        channel.parser = new V_Parser()

        channel.send("1", [
                "a": "aaaa"
        ])
        println "返回报文" + JSON.toJSONString(channel.getResult("1"));
    }

    @Test
    public void Q() {
        channel.assembly = new Q_Assembly();
        channel.parser = new Q_Parser()

        channel.send("1", [
                "ID": "1505789430419-asdcfdfgnew"
        ])
        println "返回报文" + JSON.toJSONString(channel.getResult("1"));
    }


    @Test
    public void tt() {

    }
}
package cn.wanglin.sgw

import cn.wanglin.sgw.lianlian.Bind_Assembly
import cn.wanglin.sgw.lianlian.Bind_Parser
import cn.wanglin.sgw.protocol.HttpProtocol
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class LianlianTest {
    Channel<String, String> channel = new Channel(null,null,null);

    @Before
    public void setup() {
        channel.config = new ChannelConfig(protocol: ProtocolType.HTTP,
                reqEntityType: EntityType.JSON,
                url: "https://yintong.com.cn/traderapi/bankcardbind.htm");
        ProtocolFactory protocolFactory = mock(ProtocolFactory.class)
        channel.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection(ProtocolType.HTTPS, channel)).thenReturn(new HttpProtocol(channel: channel))


    }


    @Test
    public void bind() {
        channel.assembly = new Bind_Assembly();
        channel.parser = new Bind_Parser()

        channel.send("1", [
                "a": "aaaa"
        ])
        println "返回报文" + channel.getResult("1");
    }
}

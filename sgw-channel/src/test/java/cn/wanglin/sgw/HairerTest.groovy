package cn.wanglin.sgw

import cn.wanglin.sgw.haier.T100021_Assembly
import cn.wanglin.sgw.haier.T100021_Parser
import cn.wanglin.sgw.protocol.HttpProtocol
import com.alibaba.fastjson.JSON
import org.junit.Before
import org.junit.Test

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class HairerTest {
    Channel<String, String> channel = new Channel(null,null,null);

    @Before
    public void setup() {
        channel.config = new ChannelConfig(protocol: ProtocolType.HTTP,
                reqEntityType:EntityType.TEXT,
                url: "http://testpm.haiercash.com/PaymentPlatform/api/HaiercashPayApply",
                attributes: [
                        "privateKey" : "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAuSL8Pk66MyQ87paDb02VblGOzw7b9hUwFJ4/JMi4kKLyvrR/hTd4Zm/ym75hpNfDuAJCBYpKnBq+swNvHsDBuQIDAQABAkAfCNhT8YqRDCAStrRtsgm8pntUBl+lDsQykJxxJBycUgtLK2VmdcHm/jWiJ4Ghn1JNXdNVHrxxguU0PigWVN7pAiEA6PQmY2RxB1khNemierWFwA1KPZ4E9aJlB0VFB9QAcuMCIQDLc868xyx/Y4YWxjc3rLMv4Pa7HqEeWU/GdJwMTuBvswIhALyDz4kBYEKNC0Ygsn7Q5NLdLmYcuXC6IT23/bvvAQn3AiAQRryEV77Ulia1Dbh/KL7RFsNGZwXmwqhiLRQK3AiShwIgH1iq8ie+zLgE/IBb5hhqzxV0stXscNndqwjz1tSw7WA="
                ]);
        ;
        ProtocolFactory protocolFactory = mock(ProtocolFactory.class)
        channel.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection(ProtocolType.HTTP, channel)).thenReturn(new HttpProtocol(channel: channel))


    }


    @Test
    public void V() {
        channel.assembly = new T100021_Assembly();
        channel.parser = new T100021_Parser()

        channel.send("1", [
                serno: "1111"
        ])
        println "返回报文" + JSON.toJSONString(channel.getResult("1"));
    }
}

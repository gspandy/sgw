package cn.wanglin.sgw

import cn.wanglin.sgw.Channel
import cn.wanglin.sgw.ChannelConfig
import cn.wanglin.sgw.ProtocolFactory
import cn.wanglin.sgw.zhongan.CreditApplyAssembly
import cn.wanglin.sgw.zhongan.CreditApplyParser
import cn.wanglin.sgw.zhongan.ZhongAnProtocol
import com.zhongan.scorpoin.biz.common.CommonRequest
import com.zhongan.scorpoin.biz.common.CommonResponse
import org.junit.Before;
import org.junit.Test
import static org.mockito.Mockito.*

public class ZhongAnTest {
    Channel<CommonRequest, CommonResponse> channel = new Channel();

    @Before
    public void setup() {
        channel.config = new ChannelConfig(protocol: "zhongan");
        channel.assembly = new CreditApplyAssembly();
        channel.parser = new CreditApplyParser();
        ProtocolFactory  protocolFactory = mock(ProtocolFactory.class)
        channel.protocolFactory = protocolFactory;
        when(protocolFactory.createConnection("zhongan",channel)).thenReturn(new ZhongAnProtocol(channel: channel))
    }

    @Test
    public void test() {
        def send = channel.send("1", [
                "a": "aaaa"
        ])
        println "返回报文"+ channel.getResult("1");

    }
}
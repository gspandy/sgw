package cn.wanglin.sgw.protocol

import cn.wanglin.sgw.Channel
import cn.wanglin.sgw.ChannelConfig
import org.junit.Before;
import org.junit.Test

public class HttpProtocolTest {

    HttpProtocol protocol = new HttpProtocol();
    @Before
    public void setup(){
        protocol.channel = new Channel(
                config: new ChannelConfig(
                    protocol: "http",
//                    certFile: "D:\\project\\sgw\\sgw-channel\\src\\main\\resources\\quick.keystore",
                    method: "POST",
                    url: "https://www.baidu.com"
                )
        );
    }

    @Test
    public void test(){
        try {
            protocol.send("111", "111");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
package cn.wanglin.sgw.exchange.protocol

import cn.wanglin.sgw.exchange.ExchangerConfig
import cn.wanglin.sgw.exchange.Exchanger
import org.junit.Before
import org.junit.Test

public class HttpProtocolTest {

    HttpProtocol protocol = new HttpProtocol();
    @Before
    public void setup(){
        protocol.exchanger = new Exchanger(
                config: new ExchangerConfig(
                    protocol: "http",
//                    certFile: "D:\\project\\sgw\\sgw-exchanger\\src\\main\\resources\\quick.keystore",
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
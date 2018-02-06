package cn.wanglin.sgw.exchange.protocol;


import cn.wanglin.sgw.exchange.Exchanger;
import cn.wanglin.sgw.exchange.Protocol;
import cn.wanglin.sgw.exchange.exception.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FtpProtocol <REQ, RSP> implements Protocol<REQ, RSP> {
    Logger logger = LoggerFactory.getLogger(getClass());
    Exchanger<REQ, RSP> exchanger;

    @Override
    public void send(String exchangerSequenceId, REQ request) throws TimeoutException, IOException {

    }
}

package cn.wanglin.sgw.exchange.protocol;


import cn.wanglin.sgw.exchange.Protocol;
import cn.wanglin.sgw.exchange.exception.TimeoutException;

import java.io.IOException;

public class SocketProtocol <REQ, RSP> implements Protocol<REQ, RSP> {
    @Override
    public void send(String exchangerSequenceId, REQ request) throws TimeoutException, IOException {

    }
}

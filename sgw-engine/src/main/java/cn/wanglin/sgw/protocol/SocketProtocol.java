package cn.wanglin.sgw.protocol;

import cn.wanglin.sgw.Protocol;
import cn.wanglin.sgw.exception.TimeoutException;

import java.io.IOException;

public class SocketProtocol <REQ, RSP> implements Protocol<REQ, RSP> {
    @Override
    public void send(String channelSequenceId, REQ request) throws TimeoutException, IOException {

    }
}

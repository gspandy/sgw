package cn.wanglin.sgw;


import cn.wanglin.sgw.exception.TimeoutException;

public class ProtocolFactory {
    public <REQ,RSP> Protocol<REQ,RSP> createConnection(ProtocolType protocol, Channel<REQ,RSP> params) throws TimeoutException {
        return null;
    }
}

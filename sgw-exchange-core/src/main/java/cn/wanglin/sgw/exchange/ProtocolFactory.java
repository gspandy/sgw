package cn.wanglin.sgw.exchange;



import cn.wanglin.sgw.exchange.exception.TimeoutException;

public class ProtocolFactory {
    public <REQ,RSP> Protocol<REQ,RSP> createConnection(ProtocolType protocol, Exchanger<REQ,RSP> params) throws TimeoutException {
        return null;
    }
}

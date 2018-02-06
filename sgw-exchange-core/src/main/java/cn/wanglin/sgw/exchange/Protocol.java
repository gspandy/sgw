package cn.wanglin.sgw.exchange;

import cn.wanglin.sgw.exchange.exception.TimeoutException;

import java.io.IOException;

public interface Protocol <REQ,RSP>{

    void send(String exchangerSequenceId, REQ request) throws TimeoutException,IOException;

}

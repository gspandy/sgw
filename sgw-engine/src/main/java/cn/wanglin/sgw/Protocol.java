package cn.wanglin.sgw;

import cn.wanglin.sgw.exception.TimeoutException;

import java.io.IOException;

public interface Protocol <REQ,RSP>{

    void send(String channelSequenceId, REQ request) throws TimeoutException,IOException;

}

package cn.wanglin.sgw.exception;

public class NoSuchChannelException extends Exception {
    public NoSuchChannelException(String channelCode) {
        super(channelCode);
    }
}

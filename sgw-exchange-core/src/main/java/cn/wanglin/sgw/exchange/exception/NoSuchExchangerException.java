package cn.wanglin.sgw.exchange.exception;

public class NoSuchExchangerException extends Exception {
    public NoSuchExchangerException(String exchangerCode) {
        super(exchangerCode);
    }
}

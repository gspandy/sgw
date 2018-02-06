package cn.wanglin.sgw.exchange.exception;

public class SignatureException extends Exception {
    public SignatureException() {
    }
    public SignatureException(String errorMsg) {
        super(errorMsg);
    }
}

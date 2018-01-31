package cn.wanglin.sgw.exception;

public class SignatureException extends Exception {
    public SignatureException() {
    }
    public SignatureException(String errorMsg) {
        super(errorMsg);
    }
}

package cn.wanglin.sgw.exchange;

import java.util.Map;

public class SGWResponse {

    String              bizCode;
    String              errorMsg;
    Map<String, Object> response;

    public SGWResponse(String bizCode, String errorMsg) {
        this.bizCode = bizCode;
        this.errorMsg = errorMsg;
    }

    public SGWResponse(String bizCode,Map<String, Object> response) {
        this.bizCode = bizCode;
        this.response = response;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    public void setResponse(Map<String, Object> response) {
        this.response = response;
    }
}

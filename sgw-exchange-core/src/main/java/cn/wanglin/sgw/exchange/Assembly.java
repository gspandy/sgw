package cn.wanglin.sgw.exchange;

import java.io.IOException;
import java.util.Map;

public abstract class Assembly<REQ> {
    protected ExchangerConfig exchangerConfig;


    public abstract REQ render(Map<String, Object> request) throws IOException;

    public ExchangerConfig getExchangerConfig() {
        return exchangerConfig;
    }

    public void setExchangerConfig(ExchangerConfig exchangerConfig) {
        this.exchangerConfig = exchangerConfig;
    }
}

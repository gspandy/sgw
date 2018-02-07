package cn.wanglin.sgw.exchange;

import cn.wanglin.sgw.exchange.exception.ParseException;
import cn.wanglin.sgw.exchange.exception.ServerException;
import cn.wanglin.sgw.exchange.exception.SignatureException;

public abstract class Parser<RSP> {
    protected ExchangerConfig exchangerConfig;


    public abstract ExgResponse parse(RSP exchangerResult) throws SignatureException,ParseException,ServerException;

    public ExchangerConfig getExchangerConfig() {
        return exchangerConfig;
    }

    public void setExchangerConfig(ExchangerConfig exchangerConfig) {
        this.exchangerConfig = exchangerConfig;
    }

}

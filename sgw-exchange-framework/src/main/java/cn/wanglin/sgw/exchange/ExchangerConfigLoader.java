package cn.wanglin.sgw.exchange;

import cn.wanglin.sgw.exchange.exception.NoSuchExchangerException;

public interface ExchangerConfigLoader {
    ExchangerConfig getExchangerConfig(String exchangerCode) throws NoSuchExchangerException;

}

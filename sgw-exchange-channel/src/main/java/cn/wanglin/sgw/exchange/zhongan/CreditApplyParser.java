package cn.wanglin.sgw.exchange.zhongan;

import cn.wanglin.sgw.exchange.ExchangerConfig;
import cn.wanglin.sgw.exchange.Parser;
import cn.wanglin.sgw.exchange.ExgResponse;
import cn.wanglin.sgw.exchange.exception.ParseException;
import cn.wanglin.sgw.exchange.exception.SignatureException;
import com.alibaba.fastjson.JSON;
import com.zhongan.scorpoin.biz.common.CommonResponse;

public class CreditApplyParser extends Parser<CommonResponse> {
    @Override
    public ExgResponse parse(CommonResponse exchangerResult) throws SignatureException,ParseException {
        if(null != exchangerResult.getErrorCode()){
            if(exchangerResult.getErrorCode().equals("sign-failed")) {
                throw  new SignatureException(exchangerResult.getErrorMsg());
            } else {
                throw  new ParseException(exchangerResult.getErrorMsg());
            }
        }
        return JSON.parseObject(exchangerResult.getBizContent(), ExgResponse.class);
    }

    @Override
    public ExchangerConfig getExchangerConfig() {
        return null;
    }

}

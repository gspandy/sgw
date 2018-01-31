package cn.wanglin.sgw.zhongan;

import cn.wanglin.sgw.ChannelConfig;
import cn.wanglin.sgw.SGWResponse;
import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.Parser;
import cn.wanglin.sgw.exception.SignatureException;
import com.alibaba.fastjson.JSON;
import com.zhongan.scorpoin.biz.common.CommonResponse;

import java.util.Map;

public class CreditApplyParser extends Parser<CommonResponse> {
    @Override
    public SGWResponse parse(CommonResponse channelResult) throws SignatureException,ParseException {
        if(null != channelResult.getErrorCode()){
            if(channelResult.getErrorCode().equals("sign-failed")) {
                throw  new SignatureException(channelResult.getErrorMsg());
            } else {
                throw  new ParseException(channelResult.getErrorMsg());
            }
        }
        return JSON.parseObject(channelResult.getBizContent(), SGWResponse.class);
    }

    @Override
    public ChannelConfig getChannelConfig() {
        return null;
    }

}

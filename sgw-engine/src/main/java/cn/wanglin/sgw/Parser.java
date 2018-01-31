package cn.wanglin.sgw;

import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException;

import java.util.Map;

public abstract class Parser<RSP> {
    protected ChannelConfig channelConfig;


    public abstract SGWResponse parse(RSP channelResult) throws SignatureException,ParseException,ServerException;

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
    }

}

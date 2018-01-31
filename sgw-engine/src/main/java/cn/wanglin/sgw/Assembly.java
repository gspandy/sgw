package cn.wanglin.sgw;

import java.io.IOException;
import java.util.Map;

public abstract class Assembly<REQ> {
    protected ChannelConfig channelConfig;


    public abstract REQ render(Map<String, Object> request) throws IOException;

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
    }
}

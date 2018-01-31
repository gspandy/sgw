package cn.wanglin.sgw;

import cn.wanglin.sgw.exception.NoSuchChannelException;

public interface ChannelConfigLoader {
    ChannelConfig getChannelConfig(String channelCode) throws NoSuchChannelException;

}

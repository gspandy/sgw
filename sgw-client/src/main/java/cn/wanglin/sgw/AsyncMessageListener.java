package cn.wanglin.sgw;


import java.util.Map;

public interface AsyncMessageListener  {
    void onMessage(String channelCode, String channelSequenceId, Map<String,Object> channelResponse);
}

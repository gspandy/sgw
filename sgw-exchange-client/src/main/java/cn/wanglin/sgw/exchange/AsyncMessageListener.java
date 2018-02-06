package cn.wanglin.sgw.exchange;


import java.util.Map;

public interface AsyncMessageListener  {
    void onMessage(String exchangerCode, String exchangerSequenceId, Map<String,Object> exchangerResponse);
}

package cn.wanglin.sgw;

@Domain("fin")
public interface FinChannelService extends ChannelService {
    void transfer(String out,String in ,Integer amout);
}

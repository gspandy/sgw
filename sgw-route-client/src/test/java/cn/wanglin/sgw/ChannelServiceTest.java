package cn.wanglin.sgw;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ChannelServiceTest {

    @Autowired
    FinChannelService finChannelService;

    @Test
    public void fin(){
        finChannelService.transfer("a_out","a_in",10);
    }
}
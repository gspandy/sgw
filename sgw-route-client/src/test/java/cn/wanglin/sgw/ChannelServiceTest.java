package cn.wanglin.sgw;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:sgw-client.xml"})
public class ChannelServiceTest {
//
    @Reference
    FinChannelService finChannelService;

    @Before
    public void setup(){
    }

    @Test
    public void fin(){
        finChannelService.transfer("a_out","a_in",10);
    }


}
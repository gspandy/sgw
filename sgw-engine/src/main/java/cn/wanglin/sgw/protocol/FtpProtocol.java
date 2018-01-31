package cn.wanglin.sgw.protocol;

import cn.wanglin.sgw.Channel;
import cn.wanglin.sgw.Protocol;
import cn.wanglin.sgw.exception.TimeoutException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FtpProtocol <REQ, RSP> implements Protocol<REQ, RSP> {
    Logger logger = LoggerFactory.getLogger(getClass());
    Channel<REQ, RSP> channel;

    @Override
    public void send(String channelSequenceId, REQ request) throws TimeoutException, IOException {

    }
}

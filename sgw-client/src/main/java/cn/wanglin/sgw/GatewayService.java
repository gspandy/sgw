package cn.wanglin.sgw;

import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException;
import cn.wanglin.sgw.exception.TimeoutException;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


public class GatewayService {
    ChannelConfigLoader channelConfigLoader;
    ChannelEngine engine = new ChannelEngine();

    /**
     * 请求端同步访问，等待结果返回。
     *
     * @param channelCode
     * @param channelSequenceId
     * @param channelRequest
     * @return
     * @throws TimeoutException   超时
     * @throws IOException        发送失败
     * @throws SignatureException 签名错误
     * @throws ParseException     报文解析错误
     * @throws ServerException    服务器内部错误
     */
    @SuppressWarnings("unchecked")
    public SGWResponse sync(String channelCode, String channelSequenceId, Map<String, Object> channelRequest)
            throws TimeoutException, IOException, SignatureException, ParseException, ServerException {
        return getChannel(channelCode)
                .send(channelSequenceId, channelRequest)
                .getResult(channelSequenceId);
    }

    public void async(String channelCode, String channelSequenceId, Map<String, Object> channelRequest) throws TimeoutException, IOException, ServerException {
        getChannel(channelCode)
                .send(channelSequenceId, channelRequest);
    }

    @SuppressWarnings("unchecked")
    Channel getChannel(String channelCode) throws ServerException {
        try {
            return engine.build(channelConfigLoader.getChannelConfig(channelCode));
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public void setChannelConfigLoader(ChannelConfigLoader channelConfigLoader) {
        this.channelConfigLoader = channelConfigLoader;
    }
}

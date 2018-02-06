package cn.wanglin.sgw.exchange.zhongan;

import cn.wanglin.sgw.exchange.Exchanger;
import cn.wanglin.sgw.exchange.Protocol;
import com.zhongan.scorpoin.biz.common.CommonRequest;
import com.zhongan.scorpoin.biz.common.CommonResponse;
import com.zhongan.scorpoin.common.ZhongAnApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ZhongAnProtocol implements Protocol<CommonRequest,CommonResponse> {
    Logger logger = LoggerFactory.getLogger(getClass());
    Exchanger<CommonRequest,CommonResponse> exchanger;

    @Override
    public void send(String exchangerSequenceId, CommonRequest request) throws IOException {
        try {
            String env        = "dev";
            String version    = "1.0.0";
            String appkey     = "2842652df9efa1bcd75750d81d892914";
            String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN2aLFPSrdej8SDUyUzzwd+K8e/bSAkTUJen3xM/FnwG4XpPSszvcMcIL9XOV4CL3u0gv+WfCLQRG5SwkJ0e0zyZ2ZfLQLeBEhvYNE5+o1o0wA5vwQ+s6e7aqHTWCTKKHpUqTwfaolJvRVY7NeKVjRV8PtWH/QuElM4xg7ce1JkHAgMBAAECgYEAqTLJO6s1rttvBalSld3cHomhVokwRDWqKFE1oyVdTo+WY2PdcgI0MtOOaoolB3JEYPvLaVB3Pb1+OJZjpIPftg416xexztfYxEIYQKZR2mGlC9xiiCcqWyjv462psIVV+bIldF6oR02KpnKiZMYwrclL9cw+uHyEqc+BUTk3QKECQQD9pQdvfnAR8fyj7ZtGs/nrxHxSSQXRecC1aL3mOxQJnPCDJg6DPMm6jAhhMYf7nyr1DCW0Wr0dc8fKTKPhWtCRAkEA36j41u1mLY46ZYFCXIw6uIZdkk0beSBZL1udOdjnGw77pmwuLIF7o/1Xi4Xbxb2UhzME1VCEmxIyd21XkRwcFwJBAIWBGoPN2jEn3KFwDB3P10kId6Nb+isC2bLieam4CNjyeQmeu17KXSfKUxLNXlTLt3GZvjLK2+Wnrk5FvrB7+3ECQHYtDe5iWY7Qi8J+XdYQsiY1uNNIsq95jCYdviGGnfYKIKV6m5sgf3fSsKMkA0+kMAqPxon/lVFqeAfqWk5uD9kCQQDZjeXyMdG4qpIO/ZppISHVJ9dLwWGahanO1KYdzI+Hy5U/tKTTJGkQErVZ5gUOCYzST1p+d++u0c+5ZlG3l+QV";

            ZhongAnApiClient client = new ZhongAnApiClient(env, appkey, privateKey, version);

            CommonResponse  response = (CommonResponse)  client.call(request);
            exchanger.setExchangerResponse(exchangerSequenceId, response);
            logger.info("发送交易{}报文回执:{}", exchangerSequenceId, response);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}

package cn.wanglin.sgw.exchange;

import cn.wanglin.sgw.exchange.exception.ParseException;
import cn.wanglin.sgw.exchange.exception.ServerException;
import cn.wanglin.sgw.exchange.exception.SignatureException;
import cn.wanglin.sgw.exchange.exception.TimeoutException;

import java.io.IOException;
import java.util.Map;


public class ExchangerService {
    ExchangerConfigLoader exchangerConfigLoader;
    ExchangerEngine engine = new ExchangerEngine();

    /**
     * 请求端同步访问，等待结果返回。
     *
     * @param exchangerCode
     * @param exchangerSequenceId
     * @param exchangerRequest
     * @return
     * @throws TimeoutException   超时
     * @throws IOException        发送失败
     * @throws SignatureException 签名错误
     * @throws ParseException     报文解析错误
     * @throws ServerException    服务器内部错误
     */
    @SuppressWarnings("unchecked")
    public ExgResponse sync(String exchangerCode, String exchangerSequenceId, Map<String, Object> exchangerRequest)
            throws TimeoutException, IOException, SignatureException, ParseException, ServerException {
        return getExchanger(exchangerCode)
                .send(exchangerSequenceId, exchangerRequest)
                .getResult(exchangerSequenceId);
    }

    public void async(String exchangerCode, String exchangerSequenceId, Map<String, Object> exchangerRequest) throws TimeoutException, IOException, ServerException {
        getExchanger(exchangerCode)
                .send(exchangerSequenceId, exchangerRequest);
    }

    @SuppressWarnings("unchecked")
    Exchanger getExchanger(String exchangerCode) throws ServerException {
        try {
            return engine.build(exchangerConfigLoader.getExchangerConfig(exchangerCode));
        } catch (ServerException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public void setExchangerConfigLoader(ExchangerConfigLoader exchangerConfigLoader) {
        this.exchangerConfigLoader = exchangerConfigLoader;
    }
}

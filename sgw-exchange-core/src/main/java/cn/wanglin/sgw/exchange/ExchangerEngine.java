package cn.wanglin.sgw.exchange;


import cn.wanglin.sgw.exchange.exception.ServerException;

public class ExchangerEngine {

    public Exchanger build(ExchangerConfig exchangerConfig) throws ServerException {
        return new Exchanger(exchangerConfig, assembly(exchangerConfig), parser(exchangerConfig));
    }

    public Assembly assembly(ExchangerConfig exchangerConfig) throws ServerException {
        try {
            Assembly obj = GroovyService.getObject(exchangerConfig.assemblyName, exchangerConfig.assemblyContent, Assembly.class);

            obj.exchangerConfig = exchangerConfig;
            return obj;
        } catch (Exception e) {
            throw new ServerException("初始化Assembly脚本错误,"+e.getMessage());
        }
    }

    public Parser parser(ExchangerConfig exchangerConfig) throws ServerException {
        try {
            Parser obj = GroovyService.getObject(exchangerConfig.parserName, exchangerConfig.parserContent, Parser.class);

            obj.exchangerConfig = exchangerConfig;
            return obj;
        } catch (Exception e) {
            throw new ServerException("初始化Parser脚本错误,"+e.getMessage());
        }
    }

}

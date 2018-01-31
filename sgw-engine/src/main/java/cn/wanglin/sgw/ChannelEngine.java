package cn.wanglin.sgw;


import cn.wanglin.sgw.exception.ServerException;

public class ChannelEngine {

    public Channel build(ChannelConfig channelConfig) throws ServerException {
        return new Channel(channelConfig, assembly(channelConfig), parser(channelConfig));
    }

    public Assembly assembly(ChannelConfig channelConfig) throws ServerException {
        try {
            Assembly obj = GroovyService.getObject(channelConfig.assemblyName, channelConfig.assemblyContent, Assembly.class);

            obj.channelConfig = channelConfig;
            return obj;
        } catch (Exception e) {
            throw new ServerException("初始化Assembly脚本错误,"+e.getMessage());
        }
    }

    public Parser parser(ChannelConfig channelConfig) throws ServerException {
        try {
            Parser obj = GroovyService.getObject(channelConfig.parserName, channelConfig.parserContent, Parser.class);

            obj.channelConfig = channelConfig;
            return obj;
        } catch (Exception e) {
            throw new ServerException("初始化Parser脚本错误,"+e.getMessage());
        }
    }

}

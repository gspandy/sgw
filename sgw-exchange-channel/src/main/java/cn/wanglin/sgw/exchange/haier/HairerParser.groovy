package cn.wanglin.sgw.exchange.haier

import cn.wanglin.sgw.exchange.Parser
import cn.wanglin.sgw.exchange.ExgResponse
import cn.wanglin.sgw.exchange.exception.ParseException
import cn.wanglin.sgw.exchange.exception.ServerException
import cn.wanglin.sgw.exchange.exception.SignatureException

public abstract class HairerParser extends Parser<String> {
    @Override
    public ExgResponse parse(String exchangerResult) throws SignatureException, ParseException, ServerException {
        def xml = new XmlSlurper().parseText(exchangerResult);
        if (!xml.head.retFlag.text().equals("00000")) {
            return new ExgResponse(xml.head.retFlag.text(), xml.head.retMsg.text());
        } else {
            return new ExgResponse(xml.head.retFlag.text(), trade(exchangerResult))
        }
    }

    abstract String trade(String s);
}

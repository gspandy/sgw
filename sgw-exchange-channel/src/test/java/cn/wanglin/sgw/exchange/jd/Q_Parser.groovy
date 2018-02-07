package cn.wanglin.sgw.exchange.jd

import cn.wanglin.sgw.exchange.ExgResponse
import cn.wanglin.sgw.exchange.exception.ParseException
import cn.wanglin.sgw.exchange.exception.ServerException
import cn.wanglin.sgw.exchange.exception.SignatureException

public class Q_Parser extends JDParser {
    @Override
    public ExgResponse trade(String exchangerResult) throws SignatureException, ParseException, ServerException {
        def xml = new XmlSlurper().parseText(exchangerResult)
        if(xml.RETURN.CODE.text() != "0000"){
            return new ExgResponse(xml.RETURN.CODE.text(),translateErrorCode(xml.RETURN.CODE.text()))
        }else{
            Map<String,Object> result = new HashMap<>();
            result.TYPE = xml.TRADE.TYPE.text();
            result.ID = xml.TRADE.ID.text();
            result.AMOUNT = xml.TRADE.AMOUNT.text();
            result.CURRENCY = xml.TRADE.CURRENCY.text();
            result.NOTE = xml.TRADE.NOTE.text();
            result.STATUS = xml.TRADE.STATUS.text();
            result.CURRENCY = xml.TRADE.CURRENCY.text();
            return new ExgResponse(xml.RETURN.CODE.text(),result);
        }
    }
}

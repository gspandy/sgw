package cn.wanglin.sgw.jd

import cn.wanglin.sgw.SGWResponse;
import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException

public class Q_Parser extends JDParser {
    @Override
    public SGWResponse trade(String channelResult) throws SignatureException, ParseException, ServerException {
        def xml = new XmlSlurper().parseText(channelResult)
        if(xml.RETURN.CODE.text() != "0000"){
            return new SGWResponse(xml.RETURN.CODE.text(),translateErrorCode(xml.RETURN.CODE.text()))
        }else{
            Map<String,Object> result = new HashMap<>();
            result.TYPE = xml.TRADE.TYPE.text();
            result.ID = xml.TRADE.ID.text();
            result.AMOUNT = xml.TRADE.AMOUNT.text();
            result.CURRENCY = xml.TRADE.CURRENCY.text();
            result.NOTE = xml.TRADE.NOTE.text();
            result.STATUS = xml.TRADE.STATUS.text();
            result.CURRENCY = xml.TRADE.CURRENCY.text();
            return new SGWResponse(xml.RETURN.CODE.text(),result);
        }
    }
}

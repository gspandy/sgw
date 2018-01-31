package cn.wanglin.sgw.jd

import cn.wanglin.sgw.SGWResponse;
import cn.wanglin.sgw.exception.ParseException
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException

public class V_Parser extends JDParser {
    /**
     <?xml version="1.0" encoding="UTF-8"?>
     <DATA>
     <TRADE>
     <TYPE>V</TYPE>
     <ID>1371478419979</ID>
     <AMOUNT>12345</AMOUNT>
     <CURRENCY>CNY</CURRENCY>
     </TRADE>
     <RETURN>
     <CODE>0000</CODE>
     <DESC>成功</DESC>
     </RETURN>
     </DATA>

     */
    @Override
    SGWResponse trade(String channelResult) throws SignatureException, ParseException, ServerException {
        def xml = new XmlSlurper().parseText(channelResult)
        if(xml.RETURN.CODE.text() != "0000"){
            return new SGWResponse(xml.RETURN.CODE.text(),translateErrorCode(xml.RETURN.CODE.text()))
        }else{
            Map<String,Object> result = new HashMap<>();
            result.TYPE = xml.TRADE.TYPE.text();
            result.ID = xml.TRADE.ID.text();
            result.AMOUNT = xml.TRADE.AMOUNT.text();
            result.CURRENCY = xml.TRADE.CURRENCY.text();
            return new SGWResponse(xml.RETURN.CODE.text(),result);
        }
    }

}
package cn.wanglin.sgw.haier;

import cn.wanglin.sgw.Parser
import cn.wanglin.sgw.SGWResponse;
import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException;

import java.util.Map;

public abstract class HairerParser extends Parser<String> {
    @Override
    public SGWResponse parse(String str) throws SignatureException, ParseException, ServerException {
        def xml = new XmlSlurper().parseText(str);
        if (!xml.head.retFlag.text().equals("00000")) {
            return new SGWResponse(xml.head.retFlag.text(), xml.head.retMsg.text());
        } else {
            return new SGWResponse(xml.head.retFlag.text(), trade(str))
        }
    }

    abstract String trade(String s);
}

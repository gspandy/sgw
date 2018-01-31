package cn.wanglin.sgw.lianlian

import cn.wanglin.sgw.Parser
import cn.wanglin.sgw.SGWResponse
import cn.wanglin.sgw.exception.ParseException
import cn.wanglin.sgw.exception.ServerException
import cn.wanglin.sgw.exception.SignatureException

class Bind_Parser extends Parser<String> {
    @Override
    SGWResponse parse(String channelResult) throws SignatureException, ParseException, ServerException {
        return null
    }
}

package cn.wanglin.sgw.exchange.lianlian

import cn.wanglin.sgw.exchange.Parser
import cn.wanglin.sgw.exchange.ExgResponse
import cn.wanglin.sgw.exchange.exception.ParseException
import cn.wanglin.sgw.exchange.exception.ServerException
import cn.wanglin.sgw.exchange.exception.SignatureException

class Bind_Parser extends Parser<String> {
    @Override
    ExgResponse parse(String exchangerResult) throws SignatureException, ParseException, ServerException {
        return null
    }
}

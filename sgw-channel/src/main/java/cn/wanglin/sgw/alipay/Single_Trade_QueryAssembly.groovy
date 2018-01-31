package cn.wanglin.sgw.alipay

import cn.wanglin.sgw.Assembly

class Single_Trade_QueryAssembly extends Assembly<String> {


    @Override
    String render(Map<String, Object> request) throws IOException {
        //请求报文加签 then toQueryString
        return "service=single_trade_query" +
                "&sign=d8ed9f015214e7cd59bfadb6c945a87b" +
                "&trade_no=2010121502730740" +
                "&partner=2088001159940003" +
                "&out_trade_no=2109095506028810&sign_type=MD5"
    }
}

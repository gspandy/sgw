package cn.wanglin.sgw.exchange.alipay

import cn.wanglin.sgw.exchange.Parser
import cn.wanglin.sgw.exchange.SGWResponse
import cn.wanglin.sgw.exchange.exception.ParseException
import cn.wanglin.sgw.exchange.exception.ServerException
import cn.wanglin.sgw.exchange.exception.SignatureException

class Single_Trade_QueryParser extends Parser<String> {
    /**
     // 正常返回
     "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
     "<alipay> \n" +
     "    <is_success>T</is_success>  \n" +
     "    <request> \n" +
     "        <param name=\"trade_no\">2010073000030344</param>  \n" +
     "        <param name=\"service\">single_trade_query</param>  \n" +
     "        <param name=\"partner\">2088002007018916</param>  \n" +
     "    </request> \n" +
     "    <response> \n" +
     "        <trade> \n" +
     "            <body>合同催款通知</body>  \n" +
     "            <buyer_email>ltrade008@alitest.com</buyer_email>  \n" +
     "            <buyer_id>2088102002723445</buyer_id>  \n" +
     "            <discount>0.00</discount>  \n" +
     "            <gmt_create>2010-07-30 12:26:33</gmt_create>  \n" +
     "            <gmt_last_modified_time>2010-07-30 12:30:29 \n" +
     "            </gmt_last_modified_time> \n" +
     "            <gmt_payment>2010-07-30 12:30:29</gmt_payment>  \n" +
     "            <is_total_fee_adjust>F</is_total_fee_adjust>  \n" +
     "            <out_trade_no>1280463992953</out_trade_no>  \n" +
     "            <payment_type>1</payment_type>  \n" +
     "            <price>1.00</price>  \n" +
     "            <quantity>1</quantity>  \n" +
     "            <seller_email>chao.chenc1@com</seller_email>  \n" +
     "            <seller_id>2088002007018916</seller_id>  \n" +
     "            <subject>合同催款通知</subject>  \n" +
     "            <total_fee>1.00</total_fee>  \n" +
     "            <trade_no>2010073000030344</trade_no>  \n" +
     "            <trade_status>TRADE_FINISHED</trade_status>    " +
     "            <use_coupon>F</use_coupon>  \n" +
     "        </trade> \n" +
     "    </response> \n" +
     "    <sign>56ae9c3286886f76e57e0993625c71fe</sign>  \n" +
     "    <sign_type>MD5</sign_type>  \n" +
     "</alipay>";

     //发生错误时
     "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
     "<alipay> \n" +
     "    <is_success>F</is_success> \n" +
     "    <error>TRADE_IS_NOT_EXIST</error> \n" +
     "</alipay>";
     **/
    @Override
    SGWResponse parse(String exchangerResult) throws SignatureException, ParseException, ServerException {
        def xml = new XmlSlurper().parseText(exchangerResult);
        if (xml.is_success.text().equals("F")) {
            throw new ServerException(xml.error.text());
            return new SGWResponse(false,xml.error.text())
        } else {
            Map<String,Object> result = new HashMap<>();
            result.trade_no = xml.request.param.trade_no.text();
            result.body = xml.response.trade.body.text();
            result.buyer_email = xml.response.trade.buyer_email.text();
            result.buyer_id = xml.response.trade.buyer_id.text();
            result.discount = xml.response.trade.discount.text();
            result.is_total_fee_adjust = xml.response.trade.is_total_fee_adjust.text();
            result.out_trade_no = xml.response.trade.out_trade_no.text();
            result.payment_type = xml.response.trade.payment_type.text();
            result.price = xml.response.trade.price.text();
            result.quantity = xml.response.trade.quantity.text();
            result.seller_email = xml.response.trade.seller_email.text();
            result.seller_id = xml.response.trade.seller_id.text();
            result.subject = xml.response.trade.subject.text();
            result.total_fee = xml.response.trade.total_fee.text();
            result.trade_no = xml.response.trade.trade_no.text();
            result.trade_status = xml.response.trade.trade_status.text();
            result.use_coupon = xml.response.trade.use_coupon.text();
            return new SGWResponse(result);
        }
    }


//    public static void main(String[] args){
////        println new Single_Trade_QueryParser().parse("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
////                "<alipay> \n" +
////                "    <is_success>F</is_success> \n" +
////                "    <error>TRADE_IS_NOT_EXIST</error> \n" +
////                "</alipay>")
//        println new Single_Trade_QueryParser().parse("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" +
//                "<alipay> \n" +
//                "    <is_success>T</is_success>  \n" +
//                "    <request> \n" +
//                "        <param name=\"trade_no\">2010073000030344</param>  \n" +
//                "        <param name=\"service\">single_trade_query</param>  \n" +
//                "        <param name=\"partner\">2088002007018916</param>  \n" +
//                "    </request> \n" +
//                "    <response> \n" +
//                "        <trade> \n" +
//                "            <body>合同催款通知</body>  \n" +
//                "            <buyer_email>ltrade008@alitest.com</buyer_email>  \n" +
//                "            <buyer_id>2088102002723445</buyer_id>  \n" +
//                "            <discount>0.00</discount>  \n" +
//                "            <gmt_create>2010-07-30 12:26:33</gmt_create>  \n" +
//                "            <gmt_last_modified_time>2010-07-30 12:30:29 \n" +
//                "            </gmt_last_modified_time> \n" +
//                "            <gmt_payment>2010-07-30 12:30:29</gmt_payment>  \n" +
//                "            <is_total_fee_adjust>F</is_total_fee_adjust>  \n" +
//                "            <out_trade_no>1280463992953</out_trade_no>  \n" +
//                "            <payment_type>1</payment_type>  \n" +
//                "            <price>1.00</price>  \n" +
//                "            <quantity>1</quantity>  \n" +
//                "            <seller_email>chao.chenc1@com</seller_email>  \n" +
//                "            <seller_id>2088002007018916</seller_id>  \n" +
//                "            <subject>合同催款通知</subject>  \n" +
//                "            <total_fee>1.00</total_fee>  \n" +
//                "            <trade_no>2010073000030344</trade_no>  \n" +
//                "            <trade_status>TRADE_FINISHED</trade_status>    " +
//                "            <use_coupon>F</use_coupon>  \n" +
//                "        </trade> \n" +
//                "    </response> \n" +
//                "    <sign>56ae9c3286886f76e57e0993625c71fe</sign>  \n" +
//                "    <sign_type>MD5</sign_type>  \n" +
//                "</alipay>");
//
//    }
}

package cn.wanglin.sgw;


/**
 * <p>
 * <p>
 * #基础公共数据
 * #url = http://192.168.201.37:12345/express.htm
 * url = http://localhost:12345/security_express.htm
 * charset = UTF-8
 * des = YdqJnu+wrl0vDeb++OzZMpcWq9l1qEPN
 * md5 = 1111qqqq
 * <p>
 * <p>
 * #基础信息
 * version = 1.0.0
 * <p>
 * merchant =22907856
 * terminal = 00000001
 * <p>
 * #data信息中的卡信息
 * card_bank = CMB
 * card_type = D
 * card_no =
 * #card_exp = 1408
 * #card_cvv2 = 123
 * card_name =
 * card_idtype = I
 * card_idno =
 * card_phone =
 * <p>
 * #data信息中的交易数据
 * #trade_type 在程序中指定
 * #trade_oid = 20170517163123
 * trade_id=20170523150627
 * trade_amount = 1
 * trade_currency = CNY
 * trade_date = 20170306
 * trade_time = 173527
 * trade_notice = http://www.tunle.com/jdnotify.do
 * trade_note = 测试
 * trade_limittime=1
 * trade_code = 750836
 * <p>
 * #证书信息
 * #商户私钥
 * #privateCert=key/cert.pfx
 * #privateCert=key/shuabao.pfx
 * #password=yingyinglicai
 * #password=321654
 * #publicCert=key/npp_12_001.cer
 * </p>
 *
 * @author eric
 * @version 1.0 9/18/17 1:31 AM
 * @since JDK 1.7
 */
public class ApiConstant {

//    public final static String des = "YdqJnu+wrl0vDeb++OzZMpcWq9l1qEPN";
    public final static String des = "YdqJnu+wrl0vDeb++OzZMpcWq9l1qEPN";

    public final static String charset = "UTF-8";

//    public final static String version = "1.0.0";
    public final static String version = "1.0.0";

    //    public final static String merchant = "22907856";
    public final static String merchant = "22907856";
    //    public final static String terminal = "00000001";
    public final static String terminal = "00000001";

    //    public final static String md5 = "1111qqqq";
    public final static String md5 = "1111qqqq";
    //    public final static String url = "https://wapi.jd.com/express.htm";
    public final static String url = "https://wapi.jd.com/express.htm";
    /**
     * V	签约
     * S	消费
     * Q	查询
     * R	退款
     */
    public final static String V = "V";
    public final static String S = "S";
    public final static String Q = "Q";
    public final static String R = "R";

    /**
     * 0	成功
     * 3	退款
     * 6	处理中
     * 7	失败
     */
    public final static String success = "0";
    public final static String refund = "3";
    public final static String processing = "6";
    public final static String failed = "7";

    /**
     * return code
     */
    public final static String code_0000 = "0000";
    public final static String code_0001 = "0001";

    /**
     * notice url
     */
//    public final static String jd_pay_notice_url = "http://dev.guang.com:20410/external/cashier/pay/notice/jd";
    public final static String jd_pay_notice_url = "http://dev.guang.com:20410/external/cashier/pay/notice/jd";

//    public final static String jd_repay_notice_url = "http://dev.guang.com:20410/external/cashier/repay/notice/jd";
    public final static String jd_repay_notice_url = "http://dev.guang.com:20410/external/cashier/repay/notice/jd\"";

//    public final static String jd_refund_notice_url = "http://dev.guang.com:20410/external/cashier/refund/notice/jd";
    public final static String jd_refund_notice_url = "http://dev.guang.com:20410/external/cashier/refund/notice/jd";

//    public final static String keystore_path = "dev/quick.keystore";
    public final static String keystore_path ="D:\\project\\sgw\\sgw-channel\\src\\main\\resources\\quick.keystore";

}

package cn.wanglin.sgw.jd

import cn.wanglin.sgw.SGWResponse;
import cn.wanglin.sgw.exception.ParseException;
import cn.wanglin.sgw.Parser;
import cn.wanglin.sgw.exception.ServerException;
import cn.wanglin.sgw.exception.SignatureException
import cn.wanglin.sgw.utils.Base64Utils

/**
 * resp= xml
 *
 * xml 格式同输入
 *
 * 验签 ==>业务 xml
 *
 * 异常返回部分还需要优化。
 */
public abstract class JDParser extends Parser<String> {
    static Map<String, String> dict = new HashMap<>();

    @Override
    SGWResponse parse(String result) throws SignatureException, ParseException, ServerException {
        String resp = null;
        if(null == result){
            throw IOException("返回值为空");
        }
        resp = result.replace("resp=","")
        if(null ==resp ||resp.equals("")){
            throw IOException("返回值为空");
        }
        String decodeStr =  new String(Base64Utils.decode(resp.getBytes(channelConfig.charset)));
        def commonxml = new XmlSlurper().parseText(decodeStr)

         JDUtils.verify(commonxml.VERSION.text()+  commonxml.MERCHANT.text()+ commonxml.TERMINAL.text()+ commonxml.DATA.text(),
                channelConfig.attributes.md5_key ,
                commonxml.SIGN.text()
        );
        String dataxml = JDUtils.decrypt(commonxml.DATA.text(),channelConfig.attributes.des_key,channelConfig.charset.toString())
        return trade(dataxml);
    }

    abstract SGWResponse trade(String xml);



    static {
        dict."0001" = "处理中";
        dict.EEB0001 = "该银行尚未支持，请更换银行卡";
        dict.EEB0002 = "银行签约失败";
        dict.EEB0004 = "银行交易失败";
        dict.EEB0006 = "银行签约手机号校验失败";
        dict.EEB0007 = "银行签约证件验证失败";
        dict.EEB0008 = "银行卡有效期校验失败";
        dict.EEB0009 = "银行卡CVV2校验失败";
        dict.EEB0010 = "银行卡卡类型校验失败";
        dict.EEB0012 = "银行卡账户异常";
        dict.EEB0013 = "银行卡未开通快捷业务，请与银行核实";
        dict.EEB0014 = "银行卡账户余额不足，请更换银行卡支付";
        dict.EEB0015 = "银行单笔金额超限";
        dict.EEB0016 = "银行单日金额超限";
        dict.EEB0017 = "银行日交易次数超限";
        dict.EEB0025 = "短信验证码已过期，请重新获取";
        dict.EEB0026 = "未开通短信口令";
        dict.EEB0027 = "未开通及时语短信服务功能";
        dict.EEB0028 = "交易受限，请更换银行卡支付";
        dict.EEB0029 = "银行交易金额超限，请更换银行卡支付";
        dict.EEB0030 = "银行卡号验证失败";
        dict.EEB0035 = "未开通快捷支付功能，请至网银、网点柜台银行渠道开通";
        dict.EEB0036 = "银行系统繁忙，请稍后重试";
        dict.EEB0037 = "未开通工行E支付";
        dict.EEB0038 = "已超过单月最多笔数";
        dict.EEB0039 = "已超过单月最大交易总额";
        dict.EEB0040 = "银行卡签约信息校验失败";
        dict.EEB0041 = "短信发送失败，请重新获取短信验证码";
        dict.EEB0042 = "交易不支持此卡种";
        dict.EEB0043 = "金额超限（包括：单笔金额超限、单日金额超限、超过用户卡网银金额设置金额";
        dict.EEB0044 = "银行卡签约证件类型不匹配";
        dict.EEB0045 = "客户信息校验失败或卡未开通网上支付功能";
        dict.EEB0046 = "该卡尚未开通该银行网银业务，请联系银行客服";
        dict.EEB0047 = "您的操作过于频繁，请稍后再试。";
        dict.EEB0050 = "未找到签约信息";
        dict.EEB0051 = "有效期错误，请核实后重试";
        dict.EEB0052 = "工行E支付单笔金额超限，可至银行网点申请调额";
        dict.EEB0053 = "工行E支付日累计金额超限，可至银行网点申请调额";
        dict.EEB0054 = "该卡已过期，请更换银行卡支付";
        dict.EEB0055 = "请核实有效期是否到期或删卡重试";
        dict.EEB0056 = "交易失败，请核实相关信息，若无误请联系发卡行详询原因";
        dict.EEB0057 = "卡号错误或cvv2错误次数超限请联系银行客服";
        dict.EEB0058 = "邮储银行处理中，请勿重新支付?";
        dict.EEB0060 = "该订单已被支付，系统正在处理，请勿重复支付。";
        dict.EEB0061 = "原支付申请流水已经支付，请勿重复提交";
        dict.EEB0062 = "该银行系统异常，请更换银行卡或稍后重试";
        dict.EEE0001 = "系统异常，请联系提供方研发";
        dict.EEE0002 = "网络异常";
        dict.EEE0003 = "银行异常，请稍后查询订单状态";
        dict.EEN0000 = "未配置错误信息，请联系提供方";
        dict.EEN0001 = "交易已退款，请勿重复申请";
        dict.EEN0002 = "短信验证码已失效，请重新获取。";
        dict.EEN0003 = "交易已过期，不允许再支付";
        dict.EEN0004 = "退款金额超出原支付金额";
        dict.EEN0005 = "原交易状态不能退款";
        dict.EEN0006 = "退款失败，请联系提供方研发";
        dict.EEN0007 = "提交次数过多，请稍后重试";
        dict.EEN0008 = "原单已部分退款，请勿撤销";
        dict.EEN0009 = "原单已全部退款，无需撤销";
        dict.EEN0010 = "原单未支付，无需撤销";
        dict.EEN0011 = "银行卡信息为空，请核实后重新支付";
        dict.EEN0012 = "银行卡身份证或姓名为空，请核实后重新支付";
        dict.EEN0013 = "未设置秘钥，请联系提供方相关人员生成秘钥";
        dict.EEN0014 = "短信验证次数过多，请稍后重试";
        dict.EEN0015 = "交易已成功，请勿重复支付";
        dict.EEN0016 = "交易已关闭，不允许再支付";
        dict.EEN0017 = "验证码格式不正确，请输入6位数字验证码";
        dict.EEN0018 = "未配置证书，请联系提供方相关人员配置证书";
        dict.EEN0019 = "可用余额不足";
        dict.EEN0020 = "退款金额超出可退额度";
        dict.EEN0021 = "签约时卡信息不一致";
        dict.EER0001 = "交易受限，请更换银行卡支付";
        dict.EER0002 = "单笔金额超限";
        dict.EER0003 = "日累计金额超限";
        dict.EER0004 = "月累计金额超限";
        dict.EES0001 = "报文格式错误,不能解析";
        dict.EES0002 = "非法字段，参数校验失败";
        dict.EES0004 = "会员信息异常，请检查商户号";
        dict.EES0006 = "交易异常，请联系提供方研发";
        dict.EES0007 = "报文签名错误";
        dict.EES0008 = "商户未开通此服务";
        dict.EES0009 = "非法秘钥，安全验证失败";
        dict.EES0010 = "支付银行不能为空";
        dict.EES0011 = "银行卡类型不能为空，请核实后重新支付";
        dict.EES0012 = "银行卡号验证失败";
        dict.EES0013 = "银行卡有效期校验失败";
        dict.EES0014 = "银行卡CVV2校验失败";
        dict.EES0015 = "持卡人姓名校验失败";
        dict.EES0016 = "证件类型不能为空";
        dict.EES0017 = "身份证号码校验失败";
        dict.EES0018 = "手机号校验失败";
        dict.EES0021 = "交易金额校验失败";
        dict.EES0024 = "请求时间不能为空";
        dict.EES0027 = "短信验证码校验失败";
        dict.EES0031 = "短信验证码发送失败，请稍后重试";
        dict.EES0032 = "交易已存在，请勿重复支付";
        dict.EES0035 = "短信验证码已过期，请重新获取";
        dict.EES0037 = "交易信息不存在";
        dict.EES0038 = "交易次数超限，请重新下单";
        dict.EES0041 = "短信验证错误次数超限，请稍后重试";
        dict.EES0042 = "账号货币不符";
    }

    String translateErrorCode(String code) {
        String result = dict.get(code);
        if (null == result) {
            return "服务错误";
        }
    }
}

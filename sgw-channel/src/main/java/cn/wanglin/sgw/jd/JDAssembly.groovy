package cn.wanglin.sgw.jd;

import cn.wanglin.sgw.Assembly
import cn.wanglin.sgw.utils.Base64Utils
import groovy.text.SimpleTemplateEngine
import org.apache.commons.codec.digest.DigestUtils

public abstract class JDAssembly extends Assembly<Map<String, String>> {
    protected SimpleTemplateEngine engine = new SimpleTemplateEngine();

    @Override
    public Map<String, String> render(Map<String, Object> request) throws IOException {

        //元素是交易过程中的主要数据需要用3DES加密，加密密钥需要在网银在线后台设置
        String nativeDATA = trade(request);
        System.out.println("交易报文：" + nativeDATA)
        ;
        String DATA = JDUtils.encrypt(nativeDATA, channelConfig.attributes.des_key, channelConfig.charset.toString());
        String SIGN = DigestUtils.md5Hex(channelConfig.attributes.get("VERSION") +
                channelConfig.attributes.get("MERCHANT") +
                channelConfig.attributes.get("TERMINAL") +
                DATA +
                channelConfig.attributes.get("md5_key"))
        ;

        String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<CHINABANK>\n" +
                "  <VERSION>${channelConfig.attributes.VERSION}</VERSION>\n" +
                "  <MERCHANT>${channelConfig.attributes.MERCHANT}</MERCHANT>\n" +
                "  <TERMINAL>${channelConfig.attributes.TERMINAL}</TERMINAL>\n" +
                "  <DATA>${DATA}</DATA>\n" +
                "  <SIGN>${SIGN}</SIGN>\n" +
                "</CHINABANK>";
        println("业务报文：" + template)
        Map<String, String> result = new HashMap<>();
        result.put("charset", "utf-8");
        result.put("req", Base64Utils.encode(template.getBytes()));
        return result;
    }

    protected abstract String trade(Map<String, Object> request);
}

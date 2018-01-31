package cn.wanglin.sgw.haier

import cn.wanglin.sgw.Assembly
import cn.wanglin.sgw.utils.Base64Utils
import cn.wanglin.sgw.utils.RSAUtils
import com.fasterxml.jackson.databind.ObjectMapper

abstract class HairerAssembly extends Assembly<String> {
    ObjectMapper objectMapper = new ObjectMapper();
    @Override
    String render(Map<String, Object> request) throws IOException {
        return URLEncoder.encode( objectMapper.writeValueAsString (  [
                applyNo  : request.applyNo,
                channleNo: "45",
                tradeCode: tradeCode(),
                data     : Base64Utils.encode(RSAUtils.encryptByPrivateKey(trade(request).getBytes(channelConfig.charset), channelConfig.attributes.privateKey))
        ]),channelConfig.charset.toString())
    }

    abstract String tradeCode();

    abstract String trade(Map<String, Object> request);
}

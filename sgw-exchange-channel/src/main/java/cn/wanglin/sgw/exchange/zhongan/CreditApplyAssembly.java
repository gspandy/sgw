package cn.wanglin.sgw.exchange.zhongan;

import cn.wanglin.sgw.exchange.Assembly;
import com.alibaba.fastjson.JSONObject;
import com.zhongan.scorpoin.biz.common.CommonRequest;

import java.io.IOException;
import java.util.Map;

public class CreditApplyAssembly extends Assembly<CommonRequest> {
    @Override
    public CommonRequest render(Map<String, Object> request) throws IOException {
        try {
            String serviceName = "zhongan.policy.product.query";
            CommonRequest result = new CommonRequest(serviceName);
            JSONObject    params  = new JSONObject();
            //传入业务参数
            params.put("productCode", "abc");
            params.put("productId", "123");

            result.setParams(params);
            return result;
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}

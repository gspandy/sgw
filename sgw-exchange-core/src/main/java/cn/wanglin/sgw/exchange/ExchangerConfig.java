package cn.wanglin.sgw.exchange;


import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ExchangerConfig {
    public String exchangerCode;
    public String url;

    public String certFile;
    public String certPassword;

    public ProtocolType        protocol      = ProtocolType.HTTP;
    public Endpoint            endpoint      = Endpoint.CLIENT;
    public EntityType          reqEntityType = EntityType.FROM;
    public EntityType          rspEntityType = EntityType.FROM;
    public Integer             coreSize      = 2;
    public Integer             maxSize       = 10;
    public HttpMethod          method        = HttpMethod.POST;
    public Charset             charset       = Charset.forName("utf-8");
    public Boolean             sync          = true;
    public Integer             timeout       = 3000;
    public Map<String, String> attributes    = new HashMap<String, String>();


    public String assemblyContent;
    public String assemblyName;
    public String parserContent;
    public String parserName;

    public Map<String, String> toMap() {
        Map<String, String> m = new HashMap<String, String>();
        m.put("exchangerCode", exchangerCode);
        m.put("protocol", protocol.name());
        m.put("url", url);
        m.put("charset", charset.toString());
        m.put("reqEntityType", reqEntityType.name());
        m.put("sync", sync.toString());
        m.put("timeout", timeout.toString());
        m.putAll(attributes);
        return m;
    }

}

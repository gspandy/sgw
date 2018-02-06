package cn.wanglin.sgw.exchange.jd

public class Q_Assembly extends JDAssembly {
    @Override
    protected String trade(Map<String, Object> request) {
        String template = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<DATA>\n" +
                "  <TRADE>\n" +
                "    <TYPE>Q</TYPE>\n" +
                "    <ID>${request.ID}</ID>\n" +
                "  </TRADE>\n" +
                "</DATA>"
        Map ctx = new HashMap()
        ctx.putAll(request)
        ctx.putAll(exchangerConfig.toMap())
        return engine.createTemplate(template).make(ctx).toString()
    }
}

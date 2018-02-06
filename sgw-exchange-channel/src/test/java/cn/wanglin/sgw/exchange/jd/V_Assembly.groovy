package cn.wanglin.sgw.exchange.jd

public class V_Assembly extends JDAssembly {
    @Override
    protected String trade(Map<String, Object> request) {
        String template = "<?xml version=\"1.0\"encoding=\"utf-8\"?>"+
                "<DATA>"+
                "<CARD>"+
                "<BANK>1</BANK>"+
                "<TYPE>1</TYPE>"+
                "<NO>1</NO>"+
                "<NAME>1</NAME>"+
                "<IDTYPE>1</IDTYPE>"+
                "<IDNO>1</IDNO>"+
                "<PHONE>1</PHONE>"+
                "</CARD>"+
                "<TRADE>"+
                "<TYPE>V</TYPE>"+
                "<ID>1</ID>"+
                "<AMOUNT>1</AMOUNT>"+
                "<CURRENCY>CNY</CURRENCY>"+
                "<NOTICE></NOTICE>"+
                "<NOTE></NOTE>"+
                "</TRADE>"+
                "</DATA>";
        Map ctx = new HashMap()
        ctx.putAll(request)
        ctx.putAll(exchangerConfig.toMap())
        return engine.createTemplate(template).make(ctx).toString()
    }
}

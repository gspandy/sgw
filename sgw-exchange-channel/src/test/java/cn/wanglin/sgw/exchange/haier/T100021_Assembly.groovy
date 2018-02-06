package cn.wanglin.sgw.exchange.haier

import org.joda.time.DateTime

class T100021_Assembly extends HairerAssembly{
    @Override
    String tradeCode() {
        return "100021";
    }

    /**
     <?xml version="1.0" encoding="utf-8"?>
     <request>
     <head>
     <tradeCode>100022</tradeCode>
     <serno>20160617215411453</serno>
     <sysFlag>11</sysFlag>
     <tradeType/>
     <tradeDate>2016-06-17</tradeDate>
     <tradeTime>21:54:11</tradeTime>
     <channelNo>45</channelNo>
     </head>
     <body>
     <msgTyp>01</msgTyp>
     <applSeq>954803</applSeq>
     <idNo>3715xxxxx02140532</idNo>
     <outSts>24</outSts>
     </body>
     */
    @Override
    String trade(Map<String, Object> request) {
        String date = new DateTime().toString("yyyy-MM-dd");
        String time = new DateTime().toString("HH:mm:ss");
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<request>\n" +
                "<head>\n" +
                "<tradeCode>100021</tradeCode>\n" +
                "<serno>${request.serno}</serno>\n" +
                "<sysFlag>11</sysFlag>\n" +
                "<tradeType/>\n" +
                "<tradeDate>${date}</tradeDate>\n" +
                "<tradeTime>${time}</tradeTime>\n" +
                "<channelNo>45</channelNo>\n" +
                "</head>\n" +
                "<body>\n" +
                "<msgTyp>01</msgTyp>\n" +
                "<applSeq>954803</applSeq>\n" +
                "<idNo>410821198001040038</idNo>\n" +
                "<outSts>24</outSts>\n" +
                "</body></request>";
        return xml
    }
}

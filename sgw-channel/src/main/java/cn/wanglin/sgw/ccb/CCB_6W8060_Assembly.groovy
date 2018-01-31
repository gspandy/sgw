package cn.wanglin.sgw.ccb;

import cn.wanglin.sgw.Assembly

public class CCB_6W8060_Assembly extends Assembly<String> {

    @Override
    public String render(Map<String, Object> request) {
        String template = "<?xml version=\"1.0\" encoding=\"GB18030\" standalone=\"yes\" ?>\n" +
                "<TX>\n" +
                "  <CUST_ID>JX20161018091201</CUST_ID>\n" +
                "  <USER_ID>WLPT02</USER_ID>\n" +
                "  <PASSWORD>123450</PASSWORD>\n" +
                "  <TX_CODE>6W8060</TX_CODE>\n" +
                "  <LANGUAGE>CN</LANGUAGE>\n" +
                "  <TX_INFO>\n" +
                "    <TRAN_TYPE>0</TRAN_TYPE>\n" +
                "    <PAY_ACCNO>36050185015609777777-0002</PAY_ACCNO>\n" +
                "    <CUR_TYPE>01</CUR_TYPE>\n" +
                "  </TX_INFO>\n" +
                "  <SIGN_INFO/>\n" +
                "  <SIGNCERT/>\n" +
                "</TX>";
        return template
    }
}

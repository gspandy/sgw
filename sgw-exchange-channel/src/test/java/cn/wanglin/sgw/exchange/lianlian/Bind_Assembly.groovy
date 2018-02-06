package cn.wanglin.sgw.exchange.lianlian

import cn.wanglin.sgw.exchange.Assembly

class Bind_Assembly extends Assembly<Map<String, String>> {
    @Override
    Map<String, String> render(Map<String, Object> request) throws IOException {
        return [
                card_no  : request.card_no,
                acct_name: request.acct_name,
                bind_mob : request.bind_mob,
                vali_date: request.vali_date,
                cvv2     : request.cvv2,
                id_type  : request.id_type,
                id_no    : request.id_no,
                sign_type: "MD5",
                sign     : ""
        ]
    }
}

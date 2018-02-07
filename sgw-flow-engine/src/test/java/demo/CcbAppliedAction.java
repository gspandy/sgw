package demo;

import cn.wanglin.sgw.exchange.ExchangerService;
import cn.wanglin.sgw.exchange.exception.ParseException;
import cn.wanglin.sgw.exchange.exception.ServerException;
import cn.wanglin.sgw.exchange.exception.SignatureException;
import cn.wanglin.sgw.exchange.exception.TimeoutException;

import java.io.IOException;
import java.util.Map;

public class CcbAppliedAction extends Action {
    ExchangerService exchangerService;

    public void invoke(String domain,String methodName,String sequenceId,Map<String,Object> args) throws FlowException {
        try {
            Object obj =  exchangerService.sync("ccb_apply",sequenceId,args);
            if(obj.equals("xxx1")){
                setStatus(CCBStatus.FAIL);
            }
            if(obj.equals("xxx2")){
                setStatus(CCBStatus.APPLIED);
            }
        } catch (TimeoutException e) {
            setStatus(CCBStatus.UNKOW);
        } catch (IOException e) {
            setStatus(CCBStatus.UNKOW);
        } catch (SignatureException e) {
            setStatus(CCBStatus.UNKOW);
        } catch (ParseException e) {
            setStatus(CCBStatus.UNKOW);
        } catch (ServerException e) {
            setStatus(CCBStatus.UNKOW);
        }
    }


}

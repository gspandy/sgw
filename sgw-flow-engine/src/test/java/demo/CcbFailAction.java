package demo;

import java.util.Map;

public class CcbFailAction extends Action {
    public void invoke(String domain, String methodName, String sequenceId, Map<String, Object> args) throws FlowException {
//        Object  result =  buildFailResult();
//        notifyMainThreadOrCallback(result);
    }

//    private void notifyMainThreadOrCallback(Object result) {
//
//    }

    private Object buildFailResult() {
        return null;
    }
}

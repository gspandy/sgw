package demo;



import java.util.Map;

public class CcbSuAction extends Action {
    public void invoke(String domain, String methodName, String sequenceId, Map<String, Object> args) throws FlowException {
        Object  result =  buildSuResult();
//        notifyMainThreadOrCallback(result);
    }

//    private void notifyMainThreadOrCallback(Object result) {
//
//    }

    private Object buildSuResult() {
        return null;
    }
}

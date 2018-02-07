package demo;

import java.util.Map;

public abstract class Action {
    public void invoke(String domain,String methodName,String sequenceId,Map<String,Object> args) throws FlowException{};
    protected void setStatus(CCBStatus unkow) {
    }
}

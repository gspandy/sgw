package demo;

public enum  CCBStatus{
    INIT(new CcbInitAction()),//已受理，这个状态框架应该规范掉。或者透明掉
    APPLIED(new CcbAppliedAction()),//发起下单
    UNKOW(new CcbUnkowAction()),//下单异常，提交异常等，或者其他原因结果未知
    SUBMITED(new CcbSubmitAction()),//已提交
    SU(new CcbSuAction()),
    FAIL(new CcbFailAction());

    private final Action action;

    CCBStatus(Action action) {
        this.action = action;
    }
}
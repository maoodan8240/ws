package ws.common.utils.mc.controler;

import ws.common.utils.message.interfaces.PrivateMsg;

public abstract class AbstractControler<T> implements Controler<T> {
    protected T target;
    protected Enum<?> callerAction;

    public AbstractControler() {
    }

    public AbstractControler(T target) {
        this.target = target;
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public void setTarget(T target) {
        this.target = target;
    }

    @Override
    public Enum<?> getCallerAction() {
        return this.callerAction;
    }

    @Override
    public void setCallerAction(Enum<?> callerAction) {
        this.callerAction = callerAction;
    }

    @Override
    public abstract void sendPrivateMsg(PrivateMsg privateMsg);
}

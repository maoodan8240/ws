package ws.common.utils.mc.controler;

import ws.common.utils.message.interfaces.PrivateMsg;

public interface Controler<T> {

    /**
     * 获得控制的Pojo
     *
     * @return
     */
    T getTarget();

    /**
     * 设置控制的Pojo
     *
     * @param target
     */
    void setTarget(T target);

    /**
     * 调用该Ctrl的动作
     *
     * @return
     */
    Enum<?> getCallerAction();

    /**
     * 设置调用该Ctrl的动作
     *
     * @param callerAction
     */
    void setCallerAction(Enum<?> callerAction);

    /**
     * 发送{@link PrivateMsg}给同一个下PlayerActor的所有的扩展
     *
     * @param privateMsg
     */
    void sendPrivateMsg(PrivateMsg privateMsg);
}

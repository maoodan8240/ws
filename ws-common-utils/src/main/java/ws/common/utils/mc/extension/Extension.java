package ws.common.utils.mc.extension;

import ws.common.utils.mc.controler.Controler;

/**
 * {@link Controler} 扩展包含一个对应的Controler
 * 
 * @param <T>
 */
public interface Extension<T extends Controler<?>> {

    /**
     * 设置扩展包含的Ctrl
     * 
     * @param ctrl
     */
    void setControler(T ctrl);

    /**
     * 获得扩展包含的Ctrl用于修改
     * 
     * @param callerAction
     * @return
     */
    T getControlerForUpdate(Enum<?> callerAction);

    /**
     * 获得该Control只用于查询
     * 
     * @return
     */
    T getControlerForQuery();
}

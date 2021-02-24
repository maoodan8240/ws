package ws.common.utils.mc.extension;

import ws.common.utils.mc.controler.Controler;

public abstract class AbstractExtension<T extends Controler<?>> implements Extension<T> {
    protected T ctrl;

    @Override
    public void setControler(T ctrl) {
        this.ctrl = ctrl;
    }

    @Override
    public T getControlerForUpdate(Enum<?> callerAction) {
        if (this.ctrl == null) {
            return null;
        }
        this.ctrl.setCallerAction(callerAction);
        return ctrl;
    }

    @Override
    public T getControlerForQuery() {
        return getControlerForUpdate(null);
    }
}

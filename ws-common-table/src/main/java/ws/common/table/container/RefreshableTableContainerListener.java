package ws.common.table.container;

import java.util.List;

import ws.common.table.table.interfaces.Row;

public interface RefreshableTableContainerListener {
    /**
     * 刷新前
     */
    void preRefresh();

    /**
     * 刷新后
     * 
     * @param list
     *            被刷新的表
     */
    void postRefresh(List<Class<? extends Row>> list);
}

package ws.common.table.table.implement.container;

import ws.common.table.table.interfaces.container.HierarchyName;

public class HierarchyNameBuilder {

    public static HierarchyName build(String hierarchyName) {
        return new _HierarchyName(hierarchyName);
    }

}

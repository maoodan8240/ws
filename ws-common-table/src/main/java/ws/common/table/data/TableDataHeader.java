package ws.common.table.data;

public class TableDataHeader {
    private String name; // 列名
    private String desc; // 列的描述
    private String type; // 列的类型
    private int columnIdx; // 列序号

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getColumnIdx() {
        return columnIdx;
    }


    public void setColumnIdx(int columnIdx) {
        this.columnIdx = columnIdx;
    }
}

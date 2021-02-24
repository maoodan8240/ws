package ws.common.mongoDB.handler;

public enum OrderType {
    ASC(1), // 升序
    DESC(-1); // 降序

    private int value;

    private OrderType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

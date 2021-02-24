package ws.common.utils.date;

/**
 * <pre>
 * MONDAY("星期一", 1),   TUESDAY("星期二", 2)
 * WEDNESDAY("星期三", 3) THURSDAY("星期四", 4) 
 * FRIDAY("星期五", 5)    SATURDAY("星期六", 6) 
 * SUNDAY("星期日", 7)
 * </pre>
 */
public enum WsWeekEnum {

    MONDAY("星期一", 1), TUESDAY("星期二", 2), WEDNESDAY("星期三", 3), THURSDAY("星期四", 4), FRIDAY("星期五", 5), SATURDAY("星期六", 6), SUNDAY("星期日", 7);

    private String str;
    private int value;

    private WsWeekEnum(String str, int value) {
        this.str = str;
        this.value = value;
    }

    public String getStr() {
        return str;
    }

    public int getValue() {
        return value;
    }

    public static WsWeekEnum parse(String str) {
        for (WsWeekEnum enums : values()) {
            if (enums.str.equals(str)) {
                return enums;
            }
        }
        return null;
    }
}

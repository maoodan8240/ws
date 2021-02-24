package ws.common.utils.general;

public class TrueParser {

    /**
     * 非0即为真
     * 
     * @param value
     * @return
     */
    public static boolean isTrue(int value) {
        return !(value == 0);
    }

}

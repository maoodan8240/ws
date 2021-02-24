package ws.common.utils.formula;

public class FormulaContainerUtils {

    /**
     * 以 表名_字段名_id 为格式自动生成FuncName
     *
     * @param rowClass
     * @param fieldName
     * @param id
     * @return
     */
    public static String autoGenerateFuncName(Class<?> rowClass, String fieldName, int id) {
        return rowClass.getSimpleName() + "_" + fieldName + "_" + id;
    }

    public static int convertToInt(Object evalResult, int defaultValue) {
        if (evalResult instanceof Integer) {
            return ((Integer) evalResult).intValue();
        } else if (evalResult instanceof Double) {
            if (((Double) evalResult).isNaN()) {
                return defaultValue;
            }
            return ((Double) evalResult).intValue();
        } else if (evalResult instanceof Long) {
            return ((Long) evalResult).intValue();
        }
        return defaultValue;
    }


    public static double convertToDouble(Object evalResult, double defaultValue) {
        if (evalResult instanceof Integer) {
            return ((Integer) evalResult).doubleValue();
        } else if (evalResult instanceof Double) {
            if (((Double) evalResult).isNaN()) {
                return defaultValue;
            }
            return ((Double) evalResult).doubleValue();
        } else if (evalResult instanceof Long) {
            return ((Long) evalResult).doubleValue();
        }
        return defaultValue;
    }


    public static long convertToLong(Object evalResult, long defaultValue) {
        if (evalResult instanceof Integer) {
            return ((Integer) evalResult).longValue();
        } else if (evalResult instanceof Double) {
            if (((Double) evalResult).isNaN()) {
                return defaultValue;
            }
            return ((Double) evalResult).longValue();
        } else if (evalResult instanceof Long) {
            return ((Long) evalResult).longValue();
        }
        return defaultValue;
    }
}

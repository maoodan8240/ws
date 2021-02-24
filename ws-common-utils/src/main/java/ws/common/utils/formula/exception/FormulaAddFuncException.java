package ws.common.utils.formula.exception;

public class FormulaAddFuncException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FormulaAddFuncException(String funcName, String funcScript, String formula, Throwable t) {
        super(buidMsg(funcName, funcScript, formula), t);
    }

    public static String buidMsg(String funcName, String funcScript, String formula) {
        StringBuffer sb = new StringBuffer("\n");
        sb.append("funcName").append(funcName).append("\n");
        sb.append("funcScript").append(funcScript).append("\n");
        sb.append("formula").append(formula).append("\n");
        return sb.toString();
    }
}

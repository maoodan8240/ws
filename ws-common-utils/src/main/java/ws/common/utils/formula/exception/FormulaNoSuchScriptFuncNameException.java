package ws.common.utils.formula.exception;

/**
 * 没有找到需要的公式
 * 
 */
public class FormulaNoSuchScriptFuncNameException extends RuntimeException {
    private static final long serialVersionUID = -190849719702490780L;

    public FormulaNoSuchScriptFuncNameException(String funcName) {
        this(funcName, null);
    }

    public FormulaNoSuchScriptFuncNameException(String funcName, Throwable cause) {
        super(new StringBuffer("Can't find formula name : ").append(funcName).toString(), cause);
    }
}

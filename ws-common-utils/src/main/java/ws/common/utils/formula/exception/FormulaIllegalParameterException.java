package ws.common.utils.formula.exception;

import java.util.Arrays;

/**
 * 需要增加的脚本已经存在
 * 
 */
public class FormulaIllegalParameterException extends RuntimeException {
    private static final long serialVersionUID = 1993454883646010181L;

    public FormulaIllegalParameterException(String funcName, String funcScript, Object[] args) {
        this(funcName, funcScript, args, null);
    }

    public FormulaIllegalParameterException(String funcName, String funcScript, String[] argsName, Object[] args) {
        this(funcName, funcScript, argsName, args, null);
    }

    public FormulaIllegalParameterException(String funcName, String funcScript, Object[] args, Throwable cause) {
        super(new StringBuffer("\n   Can't invokeFunction Script : \n") //
                .append("   funcName:  ").append(funcName) //
                .append("\n   funcScript:  ").append(funcScript)//
                .append("\n   args: ").append(Arrays.toString(args)) //
                .toString(), cause);
    }

    public FormulaIllegalParameterException(String funcName, String funcScript, String[] argsName, Object[] args, Throwable cause) {
        super(new StringBuffer("\n   Can't invokeFunction Script : \n") //
                .append("   funcName:  ").append(funcName) //
                .append("\n   funcScript:  ").append(funcScript)//
                .append("\n   argsName: ").append(Arrays.toString(argsName)) //
                .append("\n   args: ").append(Arrays.toString(args)) //
                .toString(), cause);
    }
}

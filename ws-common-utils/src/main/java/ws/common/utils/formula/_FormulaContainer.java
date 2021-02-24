package ws.common.utils.formula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ws.common.utils.formula.exception.FormulaAddFuncException;
import ws.common.utils.formula.exception.FormulaIllegalParameterException;
import ws.common.utils.formula.exception.FormulaInvokeFunctionException;
import ws.common.utils.formula.exception.FormulaNoSuchScriptFuncNameException;

public class _FormulaContainer implements FormulaContainer {
    private static final Logger logger = LoggerFactory.getLogger(_FormulaContainer.class);

    private ScriptEngineManager manager = new ScriptEngineManager();
    private ScriptEngine engine = manager.getEngineByName("javascript");
    private Invocable jsInvoke = (Invocable) engine;
    private Map<String, String> funcNameToFunc = new HashMap<>();
    private Map<String, List<String>> funcNameToFuncArgs = new HashMap<>();
    private static final String FUNCTION_KEYWORDS = "function";

    public boolean contains(String funcName) {
        return funcNameToFunc.keySet().contains(funcName);
    }

    public List<String> getFuncArgs(String funcName) {
        if (funcNameToFunc.containsKey(funcName)) {
            return funcNameToFuncArgs.get(funcName);
        }
        return new ArrayList<>();
    }

    public void addFunc(String funcName, String funcScript) {
        funcName = funcName.trim();
        funcScript = funcScript.trim();
        if (StringUtils.isEmpty(funcName)) {
            return;
        }
        if (StringUtils.isEmpty(funcScript)) {
            return;
        }
        String formula = null;
        if (funcScript.toLowerCase().startsWith(FUNCTION_KEYWORDS + " ")) {
            analysisCompleteFunctionScriptArgs(funcName, funcScript);
            formula = scriptFunctionNameCompletion(funcName, getScriptOfCompleteFunctionScript(funcScript));
        } else {
            formula = scriptFunctionNameCompletion(funcName, autoGenerateFuncScript(funcName, funcScript));
        }
        funcNameToFunc.put(funcName, formula);
        try {
            engine.eval(formula);
        } catch (Exception e) {
            throw new FormulaAddFuncException(funcName, funcScript, formula, e);
        }
    }

    public void addFunc(FormulaFuncName funcName, String funcScript) {
        addFunc(funcName.getFuncName(), funcScript);
    }

    public Object invokeFunction(String funcName, Object[] args) {
        checkinvokeFunctionArgs(funcName, args);
        try {
            return _invokeFunction(funcName, args);
        } catch (Exception e) {
            throw new FormulaInvokeFunctionException(e);
        }
    }

    public Object invokeFunction(FormulaFuncName funcName, Object[] args) {
        return invokeFunction(funcName.getFuncName(), args);
    }

    public Object invokeFunction(String funcName, String[] argsName, Object[] args) {
        checkinvokeFunctionArgs(funcName, argsName, args);
        args = _orderArgsByArgsName(funcName, argsName, args);
        try {
            return _invokeFunction(funcName, args);
        } catch (Exception e) {
            throw new FormulaInvokeFunctionException(e);
        }
    }

    private Object _invokeFunction(String funcName, Object[] args) throws Exception {
        Object rs = jsInvoke.invokeFunction(funcName, args);
        if (rs instanceof Double && ((Double) rs).isNaN()) {
            logger.warn("InvokeFunction Has some problem !\n     funcName={}\n     funcScript={}\n     args={}", funcName, funcNameToFunc.get(funcName), Arrays.toString(args));
        }
        return rs;
    }

    private void checkinvokeFunctionArgs(String funcName, Object[] args) {
        if (!contains(funcName)) {
            throw new FormulaNoSuchScriptFuncNameException(funcName);
        }
        if (!_checkArgsName(funcName, args)) {
            throw new FormulaIllegalParameterException(funcName, funcNameToFunc.get(funcName), args);
        }
    }

    private void checkinvokeFunctionArgs(String funcName, String[] argsName, Object[] args) {
        if (!contains(funcName)) {
            throw new FormulaNoSuchScriptFuncNameException(funcName);
        }
        if (!_checkArgsName(funcName, argsName, args)) {
            throw new FormulaIllegalParameterException(funcName, funcNameToFunc.get(funcName), argsName, args);
        }
    }

    private String scriptFunctionNameCompletion(String funcName, String funcScript) {
        return FUNCTION_KEYWORDS + " " + funcName + funcScript;
    }

    private String autoGenerateFuncScript(String funcName, String formula) {
        char[] chars = formula.toCharArray();
        List<String> paras = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (char c : chars) {
            // 变量的第一个字符
            if (sb.toString().trim().length() == 0) {
                // a-z A-Z _ $
                if (CharUtils.isAsciiAlpha(c) || c == '_' || c == '$') {
                    sb.append(c);
                } else {
                    if (sb.toString().trim().length() > 0) {
                        _addParas(paras, sb);
                    }
                    sb.setLength(0);
                    continue;
                }
            } else {
                // a-z A-Z _ $ 0-9
                if (CharUtils.isAsciiAlphanumeric(c) || c == '_' || c == '$') {
                    sb.append(c);
                } else {
                    if (sb.toString().trim().length() > 0) {
                        _addParas(paras, sb);
                    }
                    sb.setLength(0);
                    continue;
                }
            }
        }
        if (sb.toString().trim().length() > 0) {
            _addParas(paras, sb);
        }
        sb.setLength(0);
        StringBuffer funcArgs = new StringBuffer();
        if (paras.size() == 0) {
            funcArgs.append("() {return ");
        } else {
            funcArgs.append("(");
            for (int i = 0; i < paras.size(); i++) {
                if (i == paras.size() - 1) {
                    funcArgs.append(paras.get(i) + ")");
                } else {
                    funcArgs.append(paras.get(i) + ", ");
                }
            }
            funcArgs.append("{return ");
        }
        funcArgs.append(formula).append(";}");
        funcNameToFuncArgs.put(funcName, paras);
        return funcArgs.toString();
    }

    private void _addParas(List<String> paras, StringBuffer sb) {
        String para = sb.toString().trim();
        if (!paras.contains(para)) {// 过滤相同的变量
            paras.add(para);
        }
    }

    private void analysisCompleteFunctionScriptArgs(String funcName, String funcScript) {
        int leftParenthesis = funcScript.indexOf("(");
        int rightParenthesis = funcScript.indexOf(")");
        String argsAll = funcScript.substring(leftParenthesis + 1, rightParenthesis).replaceAll(" ", "");
        List<String> paras = new ArrayList<>();
        String[] argsArr = argsAll.split(",");
        for (String args : argsArr) {
            paras.add(args);
        }
        funcNameToFuncArgs.put(funcName, paras);
    }

    private String getScriptOfCompleteFunctionScript(String funcScript) {
        int leftParenthesis = funcScript.indexOf("(");
        return funcScript.substring(leftParenthesis, funcScript.length());
    }

    /**
     * 按照公式参数的顺序对输入进行排序
     * 
     * @param funcName
     * @param argsName
     * @param args
     * @return
     */
    private Object[] _orderArgsByArgsName(String funcName, String[] argsName, Object[] args) {
        Object[] argsOrder = new Object[args.length];
        List<String> argsNameOrder = funcNameToFuncArgs.get(funcName);
        List<String> argsNameOri = new ArrayList<>(Arrays.asList(argsName));
        for (int i = 0; i < argsNameOrder.size(); i++) {
            int idx = argsNameOri.indexOf(argsNameOrder.get(i));
            argsOrder[i] = args[idx];
        }
        return argsOrder;
    }

    /**
     * 
     * @param funcName
     * @param args
     * @return true 合法 false 不合法
     */
    private boolean _checkArgsName(String funcName, Object[] args) {
        if (funcNameToFuncArgs.get(funcName).size() > args.length) {
            return false;
        }
        return true;
    }

    /**
     * 检测公式的参数是否合法
     * 
     * @param funcName
     * @param argsName
     * @param args
     * @return true 合法 false 不合法
     */
    private boolean _checkArgsName(String funcName, String[] argsName, Object[] args) {
        if (funcNameToFuncArgs.get(funcName).size() > args.length || argsName.length != args.length) {
            return false;
        }
        List<String> argsNameOri = new ArrayList<>(Arrays.asList(argsName));
        if (!argsNameOri.containsAll(funcNameToFuncArgs.get(funcName))) {
            return false;
        }
        return true;
    }

    public void funcNameToFuncToString() {
        funcNameToFunc.keySet().forEach(funcName -> {
            logger.info("funcName={} funcScript={}", funcName, funcNameToFunc.get(funcName));
        });
    }
}

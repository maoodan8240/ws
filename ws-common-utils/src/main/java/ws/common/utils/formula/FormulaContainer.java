package ws.common.utils.formula;

import java.util.List;

public interface FormulaContainer {

    /**
     * 是否包含公式名
     * 
     * @param funcName
     * @return
     */
    boolean contains(String funcName);

    /**
     * 获取公式的参数
     * 
     * @param funcName
     * @return
     */
    List<String> getFuncArgs(String funcName);

    /**
     * 添加公式,已经存在的公式会被替换
     * 
     * @param funcName
     *            公式名
     * @param funcScript
     *            公式内容
     */
    void addFunc(String funcName, String funcScript);

    /**
     * 添加公式,已经存在的公式会被替换
     * 
     * @param funcName
     *            公式名
     * @param funcScript
     *            公式内容
     */
    void addFunc(FormulaFuncName funcName, String funcScript);

    /**
     * 执行公式
     * 
     * @param funcName
     * @param args
     * @return
     */
    Object invokeFunction(String funcName, Object[] args);

    /**
     * 执行公式
     * 
     * @param funcName
     * @param argsName
     * @param args
     * @return
     */
    Object invokeFunction(String funcName, String[] argsName, Object[] args);

    /**
     * 执行公式
     * 
     * @param funcName
     * @param args
     * @return
     */
    Object invokeFunction(FormulaFuncName funcName, Object[] args);

    /**
     * 打印该FormulaContainer包含的所有的公式
     */
    void funcNameToFuncToString();
}

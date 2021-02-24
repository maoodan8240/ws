package ws.common.table.table.interfaces.container;

/**
 * 层级名字
 * 
 */
public interface HierarchyName {

    /**
     * 层级分隔符
     */
    static final String SEPARATOR = "/";

    /**
     * 查询名字
     * 
     * @param index
     * @return
     */
    String get(int index);

    /**
     * 查询全名字
     * 
     * @return
     */
    String getFullName();

    /**
     * 查询层级数
     * 
     * @return
     */
    int size();

    /**
     * 是否为层级名字
     * 
     * @return
     */
    boolean isHierarchy();

    /**
     * 是否匿名
     * 
     * @return
     */
    boolean isAnonymous();

    /**
     * equals {@link #getFirstName(boolean) getFirstName(false)}
     * 
     * @return
     */
    String getFirstName();

    /**
     * 查询第一个名字
     * 
     * @param reverse
     *            是否逆序
     * @return
     */
    String getFirstName(boolean reverse);

    /**
     * equals {@link #getRemainingName(boolean) getRemainingName(false)}
     * 
     * @return
     */
    String getRemainingName();

    /**
     * 查询剩余的名字
     * 
     * @param reverse
     *            是否逆序
     * @return
     */
    String getRemainingName(boolean reverse);

    /**
     * 解析
     * 
     * @param name
     */
    void parse(String name);
}

package ws.common.table.table.implement.container;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import ws.common.table.table.interfaces.container.HierarchyName;

public class _HierarchyName implements HierarchyName, Serializable {
    private static final long serialVersionUID = 1L;

    private String[] names;

    public _HierarchyName() {
    }

    public _HierarchyName(String name) {
        parse(name);
    }

    @Override
    public String get(int index) {
        return names[index];
    }

    @Override
    public String getFullName() {
        return StringUtils.join(names, SEPARATOR);
    }

    @Override
    public int size() {
        return names.length;
    }

    @Override
    public boolean isHierarchy() {
        return size() > 1;
    }

    @Override
    public boolean isAnonymous() {
        return getFullName().length() == 0;
    }

    @Override
    public String getFirstName() {
        return getFirstName(false);
    }

    @Override
    public String getFirstName(boolean reverse) {
        int index = reverse ? (size() - 1) : 0;
        return names[index];
    }

    @Override
    public String getRemainingName() {
        return getRemainingName(false);
    }

    @Override
    public String getRemainingName(boolean reverse) {
        int size = size();
        if (size == 1) {
            return "";
        } else {
            int startIndex = reverse ? 0 : 1;
            int endIndex = reverse ? size - 1 : size;
            return StringUtils.join(names, SEPARATOR, startIndex, endIndex);
        }
    }

    @Override
    public void parse(String name) {
        String standardName = convertToStandardName(name);
        names = standardName.split("[" + SEPARATOR + "]");
    }

    /**
     * 是否为层级名字
     * 
     * @param name
     * @return
     */
    public static boolean isHierarchyName(String name) {
        return convertToStandardName(name).contains(SEPARATOR);
    }

    /**
     * 转换为标准名字
     * 
     * @param nameForConvert
     * @return
     */
    public static String convertToStandardName(String nameForConvert) {
        if (nameForConvert == null) {
            return "";
        } else {
            String result = nameForConvert.replaceAll("\\s", ""); // 删除所有空白字符
            result = result.replaceAll("[" + SEPARATOR + "]+", SEPARATOR); // 删除重复的层级分隔符
            result = result.replaceAll("^[" + SEPARATOR + "]+", ""); // 删除左边多余的层级分隔符
            result = result.replaceAll("[" + SEPARATOR + "]+$", ""); // 删除右边多余的层级分隔符
            return result;
        }
    }
}

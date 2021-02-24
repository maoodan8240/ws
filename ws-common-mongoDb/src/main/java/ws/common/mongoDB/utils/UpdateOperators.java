package ws.common.mongoDB.utils;

public enum UpdateOperators {

    /**
     * <pre>
     * {$inc:{field:value}}
     * 对一个数字字段的某个field增加value
     * </pre>
     */
    INC("$inc"),
    /**
     * <pre>
     * {$set:{field:value}}
     * 把文档中某个字段field的值设为value
     * </pre>
     */
    SET("$set"),
    /**
     * <pre>
     * {$unset:{field:1}}
     * 删除某个字段field
     * </pre>
     */
    UNSET("$unset"),
    /**
     * <pre>
     * {$push:{field:value}}
     * 把value追加到field里。注：field只能是数组类型，如果field不存在，会自动插入一个数组类型
     * </pre>
     */
    PUSH("$push"),
    /**
     * <pre>
     * {$pushAll:{field:value_array}}
     * 用法同$push一样，只是$pushAll可以一次追加多个值到一个数组字段内。
     * </pre>
     */
    PUSHALL("$pushall"),
    /**
     * <pre>
     * {$addToSet:{field:value}}
     * 加一个值到数组内，而且只有当这个值在数组中不存在时才增加。
     * </pre>
     */
    ADDTOSET("$addtoset"),
    /**
     * <pre>
     * 删除数组内第一个值：{$pop:{field:-1}}、删除数组内最后一个值：{$pop:{field:1}}
     * 用于删除数组内的一个值
     * </pre>
     */
    POP("$pop"),
    /**
     * <pre>
     * {$pull:{field:_value}}
     * 从数组field内删除一个等于_value的值
     * </pre>
     */
    PULL("$pull"),
    /**
     * <pre>
     * {$pullAll:value_array}
     * 用法同$pull一样，可以一次性删除数组内的多个值。
     * </pre>
     */
    PULLALL("$pullall"),

    /**
     * <pre>
     * {$rename:{old_field_name:new_field_name}}
     * 对字段进行重命名
     * </pre>
     */
    RENAME("$rename");

    private String operators;

    private UpdateOperators(String operators) {
        this.operators = operators;
    }

    public String getOperators() {
        return operators;
    }
}

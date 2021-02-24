package ws.common.mongoDB.interfaces.classification;

import ws.common.mongoDB.interfaces.TopLevelPojo;

public interface DeleteDao<T extends TopLevelPojo> {
    /**
     * 删除一个对象对应的数据库document--【使用默认id】--【异步】
     *
     * @param obj
     */
    void delete(T obj);
}

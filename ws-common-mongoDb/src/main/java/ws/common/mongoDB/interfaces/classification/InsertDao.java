package ws.common.mongoDB.interfaces.classification;

import ws.common.mongoDB.interfaces.TopLevelPojo;

import java.util.List;

public interface InsertDao<T extends TopLevelPojo> {

    /**
     * 保存一个对象，存在则异常
     *
     * @param obj
     */
    void insert(T obj);

    /**
     * 保存多个对象，存在则异常
     *
     * @param objLis
     */
    void insert(List<T> objLis);

    /**
     * 保存一个对象，如果存在则替换，如果不存在则创建
     *
     * @param obj
     */
    void insertIfExistThenReplace(T obj);
}

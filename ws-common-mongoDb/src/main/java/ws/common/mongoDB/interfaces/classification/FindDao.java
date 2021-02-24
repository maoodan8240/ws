package ws.common.mongoDB.interfaces.classification;

import org.bson.Document;
import ws.common.mongoDB.handler.OrderType;
import ws.common.mongoDB.interfaces.TopLevelPojo;

import java.util.List;

public interface FindDao<T extends TopLevelPojo> {

    /**
     * 查询T document所有的数据，数据量有可能很大，慎用!
     *
     * @return
     */
    List<T> findAll();

    /**
     * 根据条件查询所有符合的数据
     *
     * @param query 查询条件
     * @return
     */
    List<T> findAll(Document query);

    /**
     * 根据条件查询，并且排序
     *
     * @param query     查询条件
     * @param fieldName 排序字段
     * @param orderType 排序规则
     * @param limit     限制获取的条目数
     * @return
     */
    List<T> findAll(Document query, String fieldName, OrderType orderType, int limit);

    /**
     * ------------------------------------------------------------------------
     */

    /**
     * 查询某个objectId是否存在
     *
     * @param objectId
     * @return
     */
    boolean exists(String objectId);

    /**
     * 输出条目数
     *
     * @return
     */
    long findAllCount();

    /**
     * 根据条件输出条目数
     *
     * @param query
     * @return
     */
    long findAllCount(Document query);

    /**
     * ------------------------------------------------------------------------
     */

    /**
     * 通过objectId查询
     *
     * @param objectId
     * @return
     */
    T findOne(String objectId);

    /**
     * 通过指定条件查询
     *
     * @param query
     * @return
     */
    T findOne(Document query);
}

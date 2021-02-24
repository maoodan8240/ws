package ws.common.utils.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public interface ReentrantReadWriteLockAware {

    /**
     * 查询读写锁
     * 
     * @return
     */
    ReentrantReadWriteLock getReadWriteLock();

    /**
     * 设置读写锁
     * 
     * @param lock
     */
    void setReadWriteLock(ReentrantReadWriteLock lock);

    /**
     * 锁住读锁
     */
    public void lockRead();

    /**
     * 解开读锁
     */
    public void unlockRead();

    /**
     * 锁住写锁
     */
    public void lockWrite();

    /**
     * 解开写锁
     */
    public void unlockWrite();
}

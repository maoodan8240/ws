package ws.common.utils.concurrent;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReentrantReadWriteLockSupport implements ReentrantReadWriteLockAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantReadWriteLockSupport.class);
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReadLock readLock = lock.readLock();
    private WriteLock writeLock = lock.writeLock();

    @Override
    public ReentrantReadWriteLock getReadWriteLock() {
        return lock;
    }

    @Override
    public void setReadWriteLock(ReentrantReadWriteLock lock) {
        this.lock = lock;
    }

    /**
     * 查询读写锁状态
     * 
     * @return
     */
    private String getLockStatus() {
        return readLock.toString().replaceFirst("[^\\[]*", "") + "&" + writeLock.toString().replaceFirst("[^\\[]*", "");
    }

    /**
     * 查询状态
     * 
     * @return
     */
    private String getObjectStatus() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    @Override
    public void lockRead() {
        LOGGER.trace("Lock Read, lock={}, object={}", getLockStatus(), getObjectStatus());
        readLock.lock();
        LOGGER.trace("Locked Read, lock={}, object={}", getLockStatus(), getObjectStatus());
    }

    @Override
    public void unlockRead() {
        LOGGER.trace("Unlock Read, lock={}, object={}", getLockStatus(), getObjectStatus());
        readLock.unlock();
        LOGGER.trace("Unlocked Read, lock={}, object={}", getLockStatus(), getObjectStatus());
    }

    @Override
    public void lockWrite() {
        LOGGER.trace("Lock Write, lock={}, object={}", getLockStatus(), getObjectStatus());
        writeLock.lock();
        LOGGER.trace("Locked Write, lock={}, object={}", getLockStatus(), getObjectStatus());
    }

    @Override
    public void unlockWrite() {
        LOGGER.trace("Unlock Write, lock={}, object={}", getLockStatus(), getObjectStatus());
        writeLock.unlock();
        LOGGER.trace("Unlocked Write, lock={}, object={}", getLockStatus(), getObjectStatus());
    }
}

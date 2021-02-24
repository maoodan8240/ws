package ws.common.utils.cooldown.utils;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class GlobalTimer {

    private static final ReentrantReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final WriteLock WRITE_LOCK = READ_WRITE_LOCK.writeLock();

    private static final HashedWheelTimer TIMER = new HashedWheelTimer();

    public static Timeout newTimeout(TimerTask task, Date deadline) {
        return newTimeout(task, deadline.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public static Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
        WRITE_LOCK.lock();
        try {
            return TIMER.newTimeout(task, delay, unit);
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    public static void start() {
        WRITE_LOCK.lock();
        try {
            TIMER.start();
        } finally {
            WRITE_LOCK.unlock();
        }
    }

    public static Set<Timeout> stop() {
        WRITE_LOCK.lock();
        try {
            return TIMER.stop();
        } finally {
            WRITE_LOCK.unlock();
        }
    }

}

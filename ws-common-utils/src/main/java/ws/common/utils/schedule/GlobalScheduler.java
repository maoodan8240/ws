package ws.common.utils.schedule;

import it.sauronsoftware.cron4j.InvalidPatternException;
import it.sauronsoftware.cron4j.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GlobalScheduler {
    private static Logger logger = LoggerFactory.getLogger(GlobalScheduler.class);
    private static Scheduler scheduler;
    private static final String TASKLIST_FILE_PATH = "./tasklist.cron4j";

    public static void init() {
        scheduler = new Scheduler();
        File file = new File(TASKLIST_FILE_PATH);
        logger.debug("tasklist.cron4j 路径 ={},绝对路径={}", file.getPath(), file.getAbsolutePath());
        logger.debug("java.class.path={}", System.getProperty("java.class.path"));
        if (!file.exists()) {
            throw new RuntimeException("Schedule File {" + TASKLIST_FILE_PATH + "} not exist");
        }
        scheduler.scheduleFile(file);
        scheduler.start();
    }

    /**
     * 调度
     *
     * @param schedulingPattern
     * @param task
     * @return
     * @throws InvalidPatternException
     */
    public static String schedule(String schedulingPattern, Runnable task) throws InvalidPatternException {
        return scheduler.schedule(schedulingPattern, task);
    }

    /**
     * 取消调度
     *
     * @param id
     */
    public static void deschedule(String id) {
        scheduler.deschedule(id);
    }

}

package ws.common.utils.monitor;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _Monitor implements Monitor {
    private static final Logger logger = LoggerFactory.getLogger(_Monitor.class);
    private static final int KB = 1024;
    private static final int MB = KB * 1024;

    /**
     * 获得当前的监控对象.
     *
     * @return 返回构造好的监控对象
     * @throws Exception
     * @author GuoHuang
     */
    public MonitorInfoBean getMonitorInfoBean() throws Exception {
        // 可使用内存
        long totalMemory = Runtime.getRuntime().totalMemory() / MB;
        // 剩余内存
        long freeMemory = Runtime.getRuntime().freeMemory() / MB;
        // 使用的内存
        long useMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / MB;
        // 最大可使用内存
        long maxMemory = Runtime.getRuntime().maxMemory() / MB;
        // JDK
        String jdkInfo = System.getProperty("java.version");
        // 操作系统
        String osInfo = System.getProperty("os.name") + "-" + System.getProperty("os.version") + "  " + System.getProperty("os.arch");
        // 获得线程总数
        int processorsNum = Runtime.getRuntime().availableProcessors();
        ThreadGroup parentThread;
        for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent())
            ;
        int totalThread = parentThread.activeCount();
        // pid@hostName
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String pidHostName = runtime.getName(); // format: "pid@hostname"

        // 构造返回对象
        MonitorInfoBean infoBean = new MonitorInfoBean();
        infoBean.setJdkInfo(jdkInfo);
        infoBean.setOsInfo(osInfo);
        infoBean.setProcessorsNum(processorsNum);
        infoBean.setTotalThreadNum(totalThread);
        infoBean.setJvmTotalMemory(totalMemory);
        infoBean.setJvmUseMemory(useMemory);
        infoBean.setJvmFreeMemory(freeMemory);
        infoBean.setJvmmaxMemory(maxMemory);
        infoBean.setPidHostName(pidHostName);
        return infoBean;
    }

    public void play() throws Exception {
        MonitorInfoBean infoBean = getMonitorInfoBean();
        StringBuffer sb = new StringBuffer();
        sb.append("    JDK信息：").append("\n");
        sb.append("        JavaVersion={}").append("\n");

        sb.append("    系统信息：").append("\n");
        sb.append("        System={}").append("\n");
        sb.append("        CPU个数={}").append("\n");
        sb.append("        线程总数={}").append("\n");
        sb.append("        当前应用Pid信息={}").append("\n");
        sb.append("    JVM内存信息：").append("\n");
        sb.append("        JVM 总的内存量={} MB.").append("\n");
        sb.append("        JVM 使用内存量={} MB.").append("\n");
        sb.append("        JVM 空闲内存量={} MB.").append("\n");
        sb.append("        JVM 最大内存量={} MB.").append("\n\n");
        logger.warn("\n" + sb.toString(), infoBean.getJdkInfo(), infoBean.getOsInfo(), infoBean.getProcessorsNum(), infoBean.getTotalThreadNum(), infoBean.getPidHostName(),
                infoBean.getJvmTotalMemory(), infoBean.getJvmUseMemory(), infoBean.getJvmFreeMemory(), infoBean.getJvmmaxMemory());
    }

    public static void main(String[] args) throws Exception {
        new _Monitor().play();
    }
}
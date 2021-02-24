package ws.common.utils.monitor;

public class MonitorInfoBean {
    private long jvmTotalMemory;// 可使用内存
    private long jvmUseMemory; // 使用内存
    private long jvmFreeMemory; // 剩余内存
    private long jvmmaxMemory; // 最大可使用内存
    private String jdkInfo; // jdk版本
    private String osInfo;// 操作系统
    private String pidHostName;// pid@hostName
    private int processorsNum; // Cpu个数
    private int totalThreadNum;// 总线程数

    public long getJvmTotalMemory() {
        return jvmTotalMemory;
    }

    public void setJvmTotalMemory(long jvmTotalMemory) {
        this.jvmTotalMemory = jvmTotalMemory;
    }

    public long getJvmUseMemory() {
        return jvmUseMemory;
    }

    public void setJvmUseMemory(long jvmUseMemory) {
        this.jvmUseMemory = jvmUseMemory;
    }

    public long getJvmFreeMemory() {
        return jvmFreeMemory;
    }

    public void setJvmFreeMemory(long jvmFreeMemory) {
        this.jvmFreeMemory = jvmFreeMemory;
    }

    public long getJvmmaxMemory() {
        return jvmmaxMemory;
    }

    public void setJvmmaxMemory(long jvmmaxMemory) {
        this.jvmmaxMemory = jvmmaxMemory;
    }

    public String getJdkInfo() {
        return jdkInfo;
    }

    public void setJdkInfo(String jdkInfo) {
        this.jdkInfo = jdkInfo;
    }

    public String getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(String osInfo) {
        this.osInfo = osInfo;
    }

    public String getPidHostName() {
        return pidHostName;
    }

    public void setPidHostName(String pidHostName) {
        this.pidHostName = pidHostName;
    }

    public int getProcessorsNum() {
        return processorsNum;
    }

    public void setProcessorsNum(int processorsNum) {
        this.processorsNum = processorsNum;
    }

    public int getTotalThreadNum() {
        return totalThreadNum;
    }

    public void setTotalThreadNum(int totalThreadNum) {
        this.totalThreadNum = totalThreadNum;
    }
}
package ws.common.redis;

import java.util.List;
import java.util.Set;

import ws.common.redis.operation.In_RedisOperation;

public interface RedisOpration {
    /**
     * 初始化
     * 
     * @param maxTotal
     *            最大连接数
     * @param maxIdlel
     *            最大空闲连接数
     * @param maxWaitSeconds
     *            获取连接时的最大等待秒数
     * @param pwsd
     *            密码
     * @param masterNames
     *            sentinel的masterName
     * @param sentinelIpAndPorts
     *            sentinel的Ip和port，格式 192.168.23.88:8200
     */
    void init(int maxTotal, int maxIdlel, int maxWaitSeconds, String pwsd, List<String> masterNames, Set<String> sentinelIpAndPorts);

    /**
     * 执行redis操作
     * 
     * @param operation
     * @return
     */
    <T> T execute(In_RedisOperation operation);
}

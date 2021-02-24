package ws.common.utils.general;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.utils.exception.GetLocalAndNetIpException;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class IpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IpUtils.class);
    private static String localip; // 本地IP
    private static String netip;// 外网IP

    static {
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        // 外网IP
                        netip = ip.getHostAddress();
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        // 内网IP
                        // 多网卡的时候不好使，比如开着vmware
                    }
                }
            }
            localip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            throw new GetLocalAndNetIpException("获取内网IP地址、外网IP地址失败", e);
        } finally {
            LOGGER.debug("内网地址={}  --  外网地址={}", localip, netip);
        }
    }

    /**
     * 获取内网IP地址
     *
     * @return
     */
    public static String getLocalip() {
        return localip;
    }

    /**
     * 获取外网IP地址
     *
     * @return
     */
    public static String getNetip() {
        return netip;
    }


    public static void main(String[] args) {
        
    }
}

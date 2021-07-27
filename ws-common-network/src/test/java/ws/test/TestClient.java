package ws.test;

import com.google.protobuf.ByteString;
import drama.protos.CodesProtos;
import drama.protos.PlayerProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.client.tcp.TcpClient;
import ws.common.network.client.tcp._TcpClient;
import ws.common.network.server.config.implement._ConnConfig;
import ws.common.network.server.config.implement._ServerConfig;
import ws.common.network.server.config.interfaces.ConnConfig;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.implement._CodeToMessagePrototype;
import ws.common.network.server.interfaces.CodeToMessagePrototype;
import ws.common.network.server.interfaces.Connection;
import ws.common.network.server.interfaces.NetworkListener;
import ws.common.network.utils.EnumUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;


public class TestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestClient.class);

    public static void main(String[] args) throws InterruptedException {
        ServerConfig serverConfig = new _ServerConfig(new _ConnConfig(//
                ConnConfig.ServerProtocolType.TCP, //
                "127.0.0.1", //
                18080, //
                64, //
                320 //
        ));
        CodeToMessagePrototype ctm = new _CodeToMessagePrototype();


        TcpClient tcpClient = new _TcpClient(serverConfig);
        tcpClient.getNetworkContext().setCodeToMessagePrototype(ctm);
        tcpClient.getNetworkHandler().addListener(new NetworkListener() {
            @Override
            public void onReceive(MessageReceiveHolder receiveHolder) {
                System.out.println("client 收到消息" + receiveHolder.getMessage());
            }

            @Override
            public void onOffline(Connection connection) {
                System.out.println("client 离线了" + tcpClient.getConnection());
            }


            @Override
            public void onDisconnected(Connection connection) {
                System.out.println("client 断线了");
            }

            @Override
            public void onHeartBeating(Connection connection) {
                System.out.println("client 心跳了");
            }
        });
        tcpClient.start();
        System.out.println("发送消息了。。。。");
        PlayerProtos.Cm_HeartBeat.Builder heartBeat = PlayerProtos.Cm_HeartBeat.newBuilder();
        heartBeat.setAction(PlayerProtos.Cm_HeartBeat.Action.SYNC);
        MessageSendHolder holder = new MessageSendHolder(heartBeat.build(), EnumUtils.protoActionToString(CodesProtos.ProtoCodes.Code.Cm_HeartBeat), new ArrayList<>());
        tcpClient.getConnection().send(holder);

        PlayerProtos.Cm_GmCommand.Builder b1 = PlayerProtos.Cm_GmCommand.newBuilder();
        b1.setAction(PlayerProtos.Cm_GmCommand.Action.SEND);
        File file = new File("D:\\work_space\\xxx.png");
        byte[] bytes2 = file2byte(file);
        byte[] bytes1 = Base64.getEncoder().encode(bytes2);
        b1.setIcon(ByteString.copyFrom(bytes1));
        MessageSendHolder holder1 = new MessageSendHolder(b1.build(), "", new ArrayList<>());
        tcpClient.getConnection().send(holder1);
        Thread.sleep(30000);
        // 搜索的 的第三的的  的  的
    }

    private static byte[] file2byte(File tradeFile) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}

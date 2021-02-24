package ws.test;

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
import ws.protos.CodesProtos.ProtoCodes;
import ws.protos.PlayerProtos;
import ws.protos.PlayerProtos.Cm_GmCommand;
import ws.protos.PlayerProtos.Sm_GmCommand;

import java.util.ArrayList;

/**
 * Created by zhangweiwei on 16-8-11.
 */
public class TestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestClient.class);

    public static void main(String[] args) throws InterruptedException {
        ServerConfig serverConfig = new _ServerConfig(new _ConnConfig(//
                ConnConfig.ServerProtocolType.TCP, //
                "192.168.0.56", //
                18080, //
                64, //
                320 //
        ));
        CodeToMessagePrototype ctm = new _CodeToMessagePrototype();
        ctm.add(ProtoCodes.Code.Cm_HeartBeat_VALUE, PlayerProtos.Cm_HeartBeat.newBuilder().getDefaultInstanceForType());
        ctm.add(ProtoCodes.Code.Sm_HeartBeat_VALUE, PlayerProtos.Sm_HeartBeat.newBuilder().getDefaultInstanceForType());

        ctm.add(ProtoCodes.Code.Cm_GmCommand_VALUE, Cm_GmCommand.newBuilder().getDefaultInstanceForType());
        ctm.add(ProtoCodes.Code.Sm_GmCommand_VALUE, Sm_GmCommand.newBuilder().getDefaultInstanceForType());

        TcpClient tcpClient = new _TcpClient(serverConfig);
        tcpClient.getNetworkContext().setCodeToMessagePrototype(ctm);
        tcpClient.getNetworkHandler().addListener(new NetworkListener() {
            @Override
            public void onReceive(MessageReceiveHolder receiveHolder) {
                System.out.println("client 收到消息" + receiveHolder.getMessage());
            }

            @Override
            public void onOffline(Connection connection) {
                System.out.println("client 离线了");
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
        int i = 1;
        while (true) {
            System.out.println("发送消息了。。。。");
            PlayerProtos.Cm_HeartBeat.Builder heartBeat = PlayerProtos.Cm_HeartBeat.newBuilder();
            heartBeat.setAction(PlayerProtos.Cm_HeartBeat.Action.SYNC);
            MessageSendHolder holder = new MessageSendHolder(heartBeat.build(), "", new ArrayList<>());
            tcpClient.getConnection().send(holder);

            Cm_GmCommand.Builder b1 = Cm_GmCommand.newBuilder();
            b1.setAction(Cm_GmCommand.Action.SEND);
            b1.setContent("正在发送第" + i + "个消息...");
            MessageSendHolder holder1 = new MessageSendHolder(b1.build(), "", new ArrayList<>());
            tcpClient.getConnection().send(holder1);
            Thread.sleep(30000);
            i++;
            // 搜索的 的第三的的  的  的
        }//的的的  的 的
    }
}

package ws.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.TcpServer;
import ws.common.network.server._TcpServer;
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
import ws.protos.EnumsProtos.ErrorCodeEnum;
import ws.protos.MessageHandlerProtos;
import ws.protos.MessageHandlerProtos.Response;
import ws.protos.PlayerProtos;
import ws.protos.PlayerProtos.Cm_GmCommand;
import ws.protos.PlayerProtos.Sm_GmCommand;

import java.util.ArrayList;

/**
 * Created by zhangweiwei on 16-8-11.
 */
public class TestServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServer.class);

    public static void main(String[] args) {
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

        TcpServer tcpServer = new _TcpServer(serverConfig);
        tcpServer.getNetworkContext().setCodeToMessagePrototype(ctm);

        tcpServer.getNetworkHandler().addListener(new NetworkListener() {

            @Override
            public void onReceive(MessageReceiveHolder receiveHolder) {
                System.out.println("Server 收到消息" + receiveHolder.getMessage());
                Response.Builder resp = MessageHandlerProtos.Response.newBuilder();
                resp.setErrorCode(ErrorCodeEnum.UNKNOWN);
                resp.setResult(true);
                resp.setMsgCode(ProtoCodes.Code.Sm_GmCommand);
                Sm_GmCommand.Builder b = Sm_GmCommand.newBuilder();
                b.setAction(Sm_GmCommand.Action.RESP_SEND);
                b.setContent("回复消息了。。。。");
                resp.setSmGmCommand(b);
                MessageSendHolder holder = new MessageSendHolder(resp.build(), "", new ArrayList<>());
                receiveHolder.getConnection().send(holder);
            }

            @Override
            public void onOffline(Connection connection) {
                System.out.println("Server 离线了");
            }

            @Override
            public void onDisconnected(Connection connection) {
                System.out.println("Server 断线了");
            }

            @Override
            public void onHeartBeating(Connection connection) {
                System.out.println("Server 受到心跳消息");
            }
        });
        tcpServer.start();
    }
}

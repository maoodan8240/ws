package ws.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ws.common.network.server.tcp.TcpServer;
import ws.common.network.server.tcp._TcpServer;
import ws.protos.CodesProtos;
import ws.protos.EnumsProtos;
import ws.protos.MessageHandlerProtos;
import ws.protos.PlayerProtos.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

/**
 * Created by lee on 16-8-11.
 */
public class TestServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServer.class);

    public static void main(String[] args) {
        ServerConfig serverConfig = new _ServerConfig(new _ConnConfig(//
                ConnConfig.ServerProtocolType.TCP, //
                "127.0.0.1", //
                18080, //
                64, //
                320 //
        ));
        CodeToMessagePrototype ctm = new _CodeToMessagePrototype();


        TcpServer tcpServer = new _TcpServer(serverConfig);
        tcpServer.getNetworkContext().setCodeToMessagePrototype(ctm);

        tcpServer.getNetworkHandler().addListener(new NetworkListener() {

            @Override
            public void onReceive(MessageReceiveHolder receiveHolder) {
                System.out.println("Server 收到消息" + receiveHolder.getMessage());
                Cm_GmCommand msg = (Cm_GmCommand) receiveHolder.getMessage();
                byte[] bytes = Base64.getDecoder().decode(msg.getContentBytes().toByteArray());

                getFile(bytes, "D:\\work_space", "xx3.png");
                MessageHandlerProtos.Response.Builder resp = MessageHandlerProtos.Response.newBuilder();
                resp.setErrorCode(EnumsProtos.ErrorCodeEnum.UNKNOWN);
                resp.setResult(true);
                resp.setMsgCode(CodesProtos.ProtoCodes.Code.Sm_GmCommand);
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

    /**
     * 根据byte数组，生成文件
     */
    public static void getFile(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

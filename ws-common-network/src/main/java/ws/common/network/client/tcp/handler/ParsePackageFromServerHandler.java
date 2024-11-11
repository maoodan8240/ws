package ws.common.network.client.tcp.handler;

import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import ws.protos.MessageHandlerProtos.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.implement._TcpConnection;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

import java.util.Arrays;

public class ParsePackageFromServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParsePackageFromServerHandler.class);
    private NetworkHandler networkHandler;
    private NetworkContext networkContext;

    public ParsePackageFromServerHandler(NetworkHandler networkHandler, NetworkContext networkContext) {
        this.networkHandler = networkHandler;
        this.networkContext = networkContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        long timeRev = System.nanoTime();
        if (LOGGER.isDebugEnabled()) {
            ByteBuf byteBufCopy = byteBuf.copy();
            byte[] bsCopy = new byte[byteBufCopy.readableBytes()];
            byteBufCopy.readBytes(bsCopy);
//            LOGGER.debug("\n接收的完整字节数组为:bsCopy_length={}, bsCopy={}", bsCopy.length, Arrays.toString(bsCopy));
            bsCopy = null;
            byteBufCopy.release();
        }
        byte[] array_header = getHeaderBytes(byteBuf);
        byte[] array_body = getBodyBytes(byteBuf);
        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("\n接收的Header字节数组为:array_header_length={}, array_header={}", array_header.length, Arrays.toString(array_header));
//            LOGGER.debug("\n接收的Body字节数组为:array_body_length={}, array_body={}", array_body.length, Arrays.toString(array_body));
        }
        Header Header = build_Header_Msg(array_header);
        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("\n接收的Header为: \n{}", Header.toString());
        }
        int msgCode = Header.getMsgCode().getNumber();
        if (!networkContext.getCodeToMessagePrototype().contains(msgCode)) {
            String msg = String.format("Header attr msgCode doesn't exist! msgCode=%s", msgCode);
            throw new RuntimeException(msg);
        }
        Message cm_Message = build_Cm_Message_Msg(msgCode, array_body);
        if (LOGGER.isDebugEnabled()) {
            if (msgCode != 2) {//屏蔽心跳包,心跳包的sm_heartbeat code是2
//                if (msgCode != 10) { //10是common_config
//                    if (msgCode != 12) { //12是玩家完善信息 头像的string非常大,要注释掉不然会报OOM
                LOGGER.debug("\n接收的cm_Message为: {}\n{}", cm_Message.getClass(), TextFormat.printToUnicodeString(cm_Message));
//                    }
//                }
            }
        }
        long timeEnd = System.nanoTime();
        broadcastMessage(ctx.channel(), cm_Message, timeRev, timeEnd);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        broadcastDisconnected(ctx.channel());
        ctx.close();
    }

    /**
     * 读取Header字节数组
     *
     * @param byteBuf
     * @return
     */
    private byte[] getHeaderBytes(ByteBuf byteBuf) {
        short headerLength = byteBuf.readShort();
        byte[] array_header = new byte[headerLength];
        byteBuf.readBytes(array_header);
        return array_header;
    }

    /**
     * 读取Body字节数组
     *
     * @param byteBuf
     * @return
     */
    private byte[] getBodyBytes(ByteBuf byteBuf) {
        int bodyLength = byteBuf.readableBytes();
//        int bodyLength = byteBuf.readInt();
//        LOGGER.debug("\ngetBodyBytes.length:{},readable.length:{}", bodyLength, byteBuf.readableBytes());
        byte[] array_body = new byte[bodyLength];
        byteBuf.readBytes(array_body);
        return array_body;
    }

    /**
     * 根据header字节数组，构建Header
     *
     * @param array_header
     * @return
     */
    private Header build_Header_Msg(byte[] array_header) {
        Header header = null;
        try {
            header = Header.getDefaultInstance().getParserForType().parseFrom(array_header, 0, array_header.length);
        } catch (Exception e) {
            String msg = String.format("Net package header part parse error! array_header=%s", Arrays.toString(array_header));
            throw new RuntimeException(msg, e);
        }
        return header;
    }

    /**
     * 根据Boyd字节数组，构建Cm_Request
     *
     * @param array_body
     * @return
     */
    private Message build_Cm_Message_Msg(int msgCode, byte[] array_body) {
        Message message = null;
        try {
            message = Response.getDefaultInstance().getParserForType().parseFrom(array_body);
        } catch (Exception e) {
            String msg = String.format("Net package body part parse error! msgCode=%s array_body=%s ", msgCode, Arrays.toString(array_body));
            throw new RuntimeException(msg, e);
        }
        return message;
    }

    /**
     * 广播网络信息
     *
     * @param channel
     * @param msg
     */
    private void broadcastMessage(Channel channel, Message msg, long timeRev, long timeEnd) {
        networkHandler.onReceive(new MessageReceiveHolder(new _TcpConnection(channel), msg, timeRev, timeEnd));
    }

    /***
     * 广播断线信息
     *
     * @param channel
     */
    private void broadcastDisconnected(Channel channel) {
        networkHandler.onDisconnected(new _TcpConnection(channel));
    }
}

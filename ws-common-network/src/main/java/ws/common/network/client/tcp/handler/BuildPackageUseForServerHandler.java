package ws.common.network.client.tcp.handler;

import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import drama.protos.CodesProtos.ProtoCodes;
import drama.protos.MessageHandlerProtos.Header;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.NetworkContext;

import java.util.List;

public class BuildPackageUseForServerHandler extends MessageToMessageEncoder<MessageSendHolder> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildPackageUseForServerHandler.class);
    private NetworkContext networkContext;

    public BuildPackageUseForServerHandler(NetworkContext networkContext) {
        this.networkContext = networkContext;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageSendHolder messageAndTimesHolder, List<Object> out) throws Exception {
        Message msg = messageAndTimesHolder.getMessage();
        Header.Builder header = Header.newBuilder();
        header.setMsgCode(ProtoCodes.Code.valueOf(networkContext.getCodeToMessagePrototype().queryCode(msg.getClass())));
        header.setBodyCompressed(false);
        header.setBodyEncrypted(false);
        byte[] array_header = header.build().toByteArray();
        byte[] array_body = msg.toByteArray();
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer();
        byteBuf.writeByte(0);// 一个字节占位符
//        LOGGER.debug("array_header_length={}", array_header.length);
//        LOGGER.debug("array_body_length={}", array_body.length);
        byteBuf.writeInt(//
                MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM// 一个字节占位符
                        + MessageFrameConfig.MESSAGE_PACKAGE_TOTAL_BYTE_NUM// 但前本身所占用的字节数
                        + MessageFrameConfig.MESSAGE_PACKAGE_HEADER_BYTE_NUM// 消息帧Header长度的值所占的节数数（当前short）
                        + array_header.length + array_body.length);
        byteBuf.writeShort(array_header.length);
        byteBuf.writeBytes(array_header);
//        byteBuf.writeInt(array_body.length);
        byteBuf.writeBytes(array_body);
        if (LOGGER.isDebugEnabled()) {
            ByteBuf byteBufCopy = byteBuf.copy();
            byte[] bsCopy = new byte[byteBufCopy.readableBytes()];
            byteBufCopy.readBytes(bsCopy);
            int bLength = msg.toByteArray().length;
            float kbLength = (bLength * 1.0f) / 1024.0f;
//            LOGGER.debug("\n发送的消息体内容字节数组为={},length={}", Arrays.toString(msg.toByteArray()), msg.toByteArray().length);
//            LOGGER.debug("\n发送的完整字节数组为={},length={}", Arrays.toString(bsCopy), bsCopy.length);
            int number = header.getMsgCode().getNumber();
            if (number != 1) {//屏蔽心跳包,心跳包的Cm_heartbeat code是1 && 9是common_config && 11是玩家完善信息 头像的string非常大,要注释掉不然会报OOM
//                if (number != 9) {
//                    if (number != 11) {
                LOGGER.debug("\n发送的消息体内容长度为: bLength={} kbLength={} \n类型为={}\n内容为:\n<<\n{}>>", bLength, kbLength, msg.getClass().getSimpleName(), TextFormat.printToUnicodeString(msg));
//                    }
//                }
            }
            bsCopy = null;
            byteBufCopy.release();
        }
        out.add(byteBuf);
        msg = null;
        array_header = null;
        array_body = null;
    }
}

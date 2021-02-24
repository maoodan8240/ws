package ws.common.network.client.tcp.handler;

import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.handler.tcp.MessageSendHolder;
import ws.common.network.server.interfaces.NetworkContext;
import ws.protos.CodesProtos.ProtoCodes;
import ws.protos.MessageHandlerProtos.Header;

import java.util.Arrays;
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
        byteBuf.writeShort(//
                MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM// 一个字节占位符
                        + MessageFrameConfig.MESSAGE_PACKAGE_TOTAL_BYTE_NUM// 但前本身所占用的字节数
                        + MessageFrameConfig.MESSAGE_PACKAGE_HEADER_BYTE_NUM// 消息帧Header长度的值所占的节数数（当前short）
                        + array_header.length + array_body.length);
        byteBuf.writeShort(array_header.length);
        byteBuf.writeBytes(array_header);
        byteBuf.writeBytes(array_body);
        if (LOGGER.isDebugEnabled()) {
            ByteBuf byteBufCopy = byteBuf.copy();
            byte[] bsCopy = new byte[byteBufCopy.readableBytes()];
            byteBufCopy.readBytes(bsCopy);
            int bLength = msg.toByteArray().length;
            float kbLength = (bLength * 1.0f) / 1024.0f;
            LOGGER.debug("\n发送的消息体内容字节数组为={}", Arrays.toString(msg.toByteArray()));
            LOGGER.debug("\n发送的完整字节数组为={}", Arrays.toString(bsCopy));
            LOGGER.debug("\n发送的消息体内容长度为: bLength={} kbLength={} \n类型为={}\n内容为:\n<<\n{}>>", bLength, kbLength, msg.getClass().getSimpleName(), TextFormat.printToUnicodeString(msg));
            bsCopy = null;
            byteBufCopy.release();
        }
        out.add(byteBuf);
        msg = null;
        array_header = null;
        array_body = null;
    }
}

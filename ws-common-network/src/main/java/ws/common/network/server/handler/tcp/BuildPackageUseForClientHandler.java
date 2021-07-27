package ws.common.network.server.handler.tcp;

import com.google.protobuf.Message;
import com.google.protobuf.TextFormat;
import drama.protos.MessageHandlerProtos.Header;
import drama.protos.MessageHandlerProtos.Response;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.interfaces.NetworkContext;

import java.util.List;

public class BuildPackageUseForClientHandler extends MessageToMessageEncoder<MessageSendHolder> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuildPackageUseForClientHandler.class);
    @SuppressWarnings("unused")
    private NetworkContext networkContext;

    public BuildPackageUseForClientHandler(NetworkContext networkContext) {
        this.networkContext = networkContext;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageSendHolder sendHolder, List<Object> out) throws Exception {
        Message msg = sendHolder.getMessage();
        sendHolder.getTimes().add(System.nanoTime());
        Header.Builder header = Header.newBuilder();
        Response response = (Response) msg;
        header.setMsgCode(response.getMsgCode());
        header.setBodyCompressed(false);
        header.setBodyEncrypted(false);
        byte[] array_header = header.build().toByteArray();
        byte[] array_body = msg.toByteArray();
//        LOGGER.debug("array_header_length={}", array_header.length);
//        LOGGER.debug("array_body_length={}", array_body.length);
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer();
        byteBuf.writeByte(0);// 一个字节占位符
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
            //以下是单独对这个心跳可common_config的特殊处理,不是通用框架中的逻辑,其他项目可以删除这个判断
            if (response.getMsgCode().getNumber() != 2) {//屏蔽心跳包,心跳包的Sm_heartbeat code是2
                if (response.getMsgCode().getNumber() != 10) {//10是common_config
                    if (response.getMsgCode().getNumber() != 12) { //12是玩家完善信息 头像的string非常大,要注释掉不然会报OOM
                        LOGGER.error("\n发送的消息体内容长度为: bLength={} kbLength={} \n类型为={}\n内容为:\n<<\n{}>>", bLength, kbLength, msg.getClass().getSimpleName(), TextFormat.printToUnicodeString(msg));
                    }
                }
            }
            bsCopy = null;
            byteBufCopy.release();
        }
        sendHolder.getTimes().add(System.nanoTime());
        displayTimesConsume(sendHolder.getMsgAction(), sendHolder.getTimes());
        out.add(byteBuf);
        sendHolder.getTimes().clear();
        sendHolder = null;
        msg = null;
        array_header = null;
        array_body = null;
    }

    public static void displayTimesConsume(String msgActionName, List<Long> times) {
        // LOGGER.debug("{} -> 耗时节点:{}", msgActionName, times);
        String[] names = { //
                "解析ClientRequest       耗时： ", //
                "gateway传送至WorldActor 耗时： ", //
                "PlayerActor Handle      耗时： ", //
                "Response传送至gateway   耗时： ", //
                "组装ServerResponse      耗时： ", //
                "接收到发出              耗时： "//
        };
        if (times.size() == 10) {
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (i = 0; i < times.size() - 1; i += 2) {
                sb.append(names[i / 2]).append(((times.get(i + 1) - times.get(i)) * 1.0) / 1000000).append("\n");
            }
            sb.append(names[i / 2]).append(":").append(((times.get(i - 1) - times.get(0)) * 1.0) / 1000000).append("\n");
            LOGGER.debug("\n{} -> 流程 耗时： \n{}", msgActionName, sb.toString());
        }
    }
}

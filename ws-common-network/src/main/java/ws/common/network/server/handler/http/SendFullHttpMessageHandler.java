package ws.common.network.server.handler.http;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.utils.HandlerUtils;
import ws.common.network.utils.ZLibUtils;

public abstract class SendFullHttpMessageHandler extends MessageToMessageEncoder<Message> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendFullHttpMessageHandler.class);
    protected NetworkContext networkContext;

    public SendFullHttpMessageHandler(NetworkContext networkContext) {
        this.networkContext = networkContext;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> out) throws Exception {
        if (!networkContext.getCodeToMessagePrototype().contains(message.getClass())) {
            LOGGER.error("发送的message={},不存在Code码！", message.getClass());
            return;
        }
        if (networkContext.getCodeToMessagePrototype().contains(message.getClass())) {
            // 获取协议号
            int code = networkContext.getCodeToMessagePrototype().queryCode(message.getClass());
            byte[] array_body = message.toByteArray();
            // 如果有必要，则压缩发送的数据
            boolean bodyCompressed = false;
            if (array_body.length > MessageFrameConfig.MESSAGE_MAX_COMPRESS_LEN) {
                array_body = ZLibUtils.compress(array_body);
                bodyCompressed = true;
            }
            // 协议头为固定的10位长度
            byte[] array_header_all = HandlerUtils.buildFullHeader(code, bodyCompressed);
            // 组装一个完整的消息
            byte[] array_msg_all = HandlerUtils.buildFullMsg(array_header_all, array_body);
            write(out, array_msg_all);
        }
    }

    protected abstract void write(List<Object> out, byte[] array_msg_all);
}

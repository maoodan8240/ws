package ws.common.network.client.http.handler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.utils.HandlerUtils;
import ws.protos.MessageHandlerProtos.Header;

public class HttpProtobufResponseHandler extends SimpleChannelInboundHandler<FullHttpResponse> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProtobufResponseHandler.class);
    private NetworkContext networkContext;

    public HttpProtobufResponseHandler(NetworkContext networkContext) {
        this.networkContext = networkContext;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws InvalidProtocolBufferException {
        // 所有字节内容
        ByteBuf all = response.content();
        // 协议头所有字节内容
        ByteBuf byteBuf_header_all = all.readBytes(MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM);
        byte[] byte__header_all = new byte[MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM];
        byteBuf_header_all.readBytes(byte__header_all);
        HandlerUtils.release(byteBuf_header_all);
        // 协议头真实的内容
        int idx = getZeroIndex(byte__header_all);
        byte[] byte__header = new byte[MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM - idx];
        System.arraycopy(byte__header_all, idx, byte__header, 0, MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM - idx);

        // 构建协议头消息对象
        Header header = Header.getDefaultInstance().getParserForType().parseFrom(byte__header);
        byte[] byte_body = new byte[all.readableBytes()];
        all.readBytes(byte_body);

        Message messageBody = null;// networkContext.getCodeToMessagePrototype().query(header.getCode());
        Message message = messageBody.getDefaultInstanceForType().getParserForType().parseFrom(byte_body);
        LOGGER.info("\n收到的消息：type={}  \n    content={}", messageBody.getClass(), message);
    }

    private int getZeroIndex(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}

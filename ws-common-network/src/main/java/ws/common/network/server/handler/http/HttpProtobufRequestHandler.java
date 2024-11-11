package ws.common.network.server.handler.http;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.handler.tcp.MessageReceiveHolder;
import ws.common.network.server.implement._HttpConnection;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;
import ws.common.network.utils.HttpUtils;
import ws.protos.MessageHandlerProtos.*;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpProtobufRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProtobufRequestHandler.class);
    private NetworkHandler networkHandler;
    private NetworkContext networkContext;

    public HttpProtobufRequestHandler(NetworkHandler networkHandler, NetworkContext networkContext) {
        this.networkHandler = networkHandler;
        this.networkContext = networkContext;
    }

    @Override
    /**
     * <pre>
     * ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
     * ctx.channel().close() ctx.pipeline().close() ctx.close();
     * 关闭后 channelRead0 依然可能被执行多次，因为该链接上有可能在很短的时间接收来多次请求
     * </pre>
     */
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        long timeRev = System.nanoTime();
        System.out.println("uri=>" + req.getUri());
        System.out.println(req.headers().names());
        System.out.println("=======================");
        for (Entry<String, String> s : req.headers().entries()) {
            System.out.println(s.getKey() + " -- " + s.getValue());
        }
        System.out.println("=======================");
        try {
            if (!checkFullHttpRequest(ctx, req)) {
                return;
            }
            prarseFullHttpRequestContent(ctx, req, timeRev);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    private boolean checkFullHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.getDecoderResult().isSuccess()) { // 解析结果
            HttpUtils.sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return false;
        }
        if (req.getMethod() != GET) {// 只处理Get请求
            HttpUtils.sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return false;
        }
        if ("/".equals(req.getUri())) { // /
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(new byte[]{}));
            response.headers().set(CONTENT_TYPE, HttpUtils.CONTENT_TYPE_HTML_VALUE);
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            HttpUtils.sendHttpResponse(ctx, req, response);
            return false;
        }
        if ("/favicon.ico".equals(req.getUri())) {// 处理 favicon.ico
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            HttpUtils.sendHttpResponse(ctx, req, res);
            return false;
        }
        if (req.content().readableBytes() == 0) {
            HttpUtils.sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return false;
        }
        return true;
    }

    private void prarseFullHttpRequestContent(ChannelHandlerContext ctx, FullHttpRequest req, long timeRev) {
        // CP/IP协议是按照大端传输方式，如果不是大端，则需要转为大端
        ByteBuf byteBuf_All = req.content();
        LOGGER.info("接受到网络传输内容，其大小端传输方式为{} !", byteBuf_All.order());
        if (byteBuf_All.order() != ByteOrder.BIG_ENDIAN) {
            byteBuf_All = byteBuf_All.order(ByteOrder.BIG_ENDIAN);
        }
        /** 获取协议头字节数组并且构建协议头对象 */
        byte[] array_header = getHeaderBytes(byteBuf_All);
        int bodyCode = 0;
        try {
            Header Header = createHeaderProtobufMsg(array_header);
            bodyCode = 0;// Header.getCode();
        } catch (InvalidProtocolBufferException e) {
            LOGGER.error("构建协议头发生异常，array_header={} ", Arrays.toString(array_header), e);
            return;
        }

        if (!networkContext.getCodeToMessagePrototype().contains(bodyCode)) {
            LOGGER.error("成功解析code={},但是协议号不存在！ ", bodyCode);
            return;
        }

        /** 获取协议体字节数组并且构建协议头对象 */
        byte[] array_body = getBodyBytes(byteBuf_All);
        Message messageBody = null;
        try {
            messageBody = createBodyProtobufMsg(bodyCode, array_body);
        } catch (InvalidProtocolBufferException e) {
            LOGGER.error("构建协议体发生异常，code={} array_body={} ", bodyCode, Arrays.toString(array_body), e);
            return;
        }

        if (messageBody == null) {
            LOGGER.error("构建协议体失败，code={} array_body={} ", bodyCode, Arrays.toString(array_body));
            return;
        }
        array_header = null;
        array_body = null;
        byteBuf_All = null;
        long timeEnd = System.nanoTime();
        networkHandler.onReceive(new MessageReceiveHolder(new _HttpConnection(ctx, req), messageBody, timeRev, timeEnd));
    }

    @SuppressWarnings("unused")
    private byte[] getAllProtoBytesByteParseContent(FullHttpRequest req) {
        byte[] protoBytes = new byte[req.content().readableBytes()];
        req.content().readBytes(protoBytes);
        return protoBytes;
    }

    @SuppressWarnings("unused")
    private byte[] getAllProtoBytesByteParseUri(FullHttpRequest req) throws UnsupportedEncodingException {
        // get请求
        QueryStringDecoder decoderQuery = new QueryStringDecoder(req.getUri());
        Map<String, List<String>> uriAttributes = decoderQuery.parameters();
        String protoBytesStr = uriAttributes.get("header").get(0);
        return protoBytesStr.getBytes("UTF-8");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("channel 发生异常，channel 准备关闭！", cause);
        ctx.close();
    }

    /**
     * 读取Header字节数组
     *
     * @param header_body
     * @return
     */
    private byte[] getHeaderBytes(ByteBuf byteBuf_All) {
        // 读取固定长度的协议头所有字节
        ByteBuf byteBuf_Header = byteBuf_All.readBytes(MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM);
        // byteBuf_Header转字节数组
        byte[] array_header_zero = new byte[byteBuf_Header.readableBytes()];
        byteBuf_Header.readBytes(array_header_zero);
        release(byteBuf_Header);
        // 协议头字节数组前面填补0的index
        int indx = getZeroIndex(array_header_zero);
        // 读取协议头真实的字节数组
        byte[] array_header = new byte[MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM - indx];
        System.arraycopy(array_header_zero, indx, array_header, 0, array_header.length);
        array_header_zero = null;
        byteBuf_Header = null;
        return array_header;
    }

    private byte[] getBodyBytes(ByteBuf byteBuf_All) {
        byte[] array_body = new byte[byteBuf_All.readableBytes()];
        byteBuf_All.readBytes(array_body);
        byteBuf_All = null;
        return array_body;
    }

    private int getZeroIndex(byte[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0) {
                return i;
            }
        }
        return 0;
    }

    private Header createHeaderProtobufMsg(byte[] array_header) throws InvalidProtocolBufferException {
        return Header.getDefaultInstance().getParserForType().parseFrom(array_header, 0, array_header.length);
    }

    private Message createBodyProtobufMsg(int code, byte[] array_body) throws InvalidProtocolBufferException {
        return networkContext.getCodeToMessagePrototype().query(code).newBuilderForType().mergeFrom(array_body).build();
    }

    private void release(ByteBuf byteBuf) {
        if (byteBuf.refCnt() != 0) {
            ReferenceCountUtil.release(byteBuf);
        }
    }
}

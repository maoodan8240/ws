package ws.common.network.utils;

import drama.protos.MessageHandlerProtos.Header;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;
import ws.common.network.server.config.MessageFrameConfig;

public class HandlerUtils {

  
    public static void release(ByteBuf byteBuf) {
        if (byteBuf.refCnt() != 0) {
            ReferenceCountUtil.release(byteBuf);
        }
    }

    public static byte[] buildFullHeader(int code, boolean bodyCompressed) {
        Header.Builder b = Header.newBuilder();
        // b.setCode(code);
        b.setBodyCompressed(bodyCompressed);
        byte[] array_header = b.build().toByteArray();
        byte[] array_header_all = new byte[MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM];
        System.arraycopy(array_header, 0, array_header_all, MessageFrameConfig.MESSAGE_HEADER_NEED_BYTE_NUM - array_header.length, array_header.length);
        return array_header_all;
    }

    public static byte[] buildFullMsg(byte[] array_header_all, byte[] array_body) {
        byte[] all = new byte[array_header_all.length + array_body.length];
        System.arraycopy(array_header_all, 0, all, 0, array_header_all.length);
        System.arraycopy(array_body, 0, all, array_header_all.length, array_body.length);
        return all;
    }

}

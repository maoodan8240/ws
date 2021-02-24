package ws.common.network.server.initializer;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.ReadTimeoutHandler;
import ws.common.network.server.config.interfaces.ServerConfig;
import ws.common.network.server.handler.http.HttpProtobufRequestHandler;
import ws.common.network.server.handler.http.SendFullHttpResponseHandler;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

public class HttpServerInitializer {
    private ChannelPipeline pipeline;

    public HttpServerInitializer(ChannelPipeline pipeline) {
        this.pipeline = pipeline;
    }

    /**
     * <pre>
     * pipeline.addLast("WriteTimeoutHandler", new WriteTimeoutHandler(1, TimeUnit.SECONDS));// 用于控制数据输出的时候的超时，构造参数1表示如果持续1秒钟都没有数据写了，那么就超时。
     * </pre>
     */
    public void initChannel(ServerConfig serverConfig, NetworkHandler networkHandler, NetworkContext networkContext) {

        pipeline.addLast("ReadTimeoutHandler", new ReadTimeoutHandler(serverConfig.getConnConfig().getReadTimeout(), TimeUnit.SECONDS));// 用于控制读取数据的时候的超时，10表示如果10秒钟都没有数据读取了，那么就引发超时，然后关闭当前的channel
        pipeline.addLast("ServerCodec", new HttpServerCodec(4096, 8192, 8192)); // 默认大小，可以调节, HttpServerCodec 处理 HttpRequestDecoder 和 HttpResponseEncoder
        pipeline.addLast("Aggregator", new HttpObjectAggregator(4096 * 10));// 定义缓冲数据量, 通常接收到的是一个http片段，如果要想完整接受一次请求的所有数据， 需要绑定HttpObjectAggregator, 就可以收到一个FullHttpRequest-是一个完整的请求信息。

        pipeline.addLast("Compressor", new HttpContentCompressor()); // 压缩response
        pipeline.addLast("SendFullHttpResponseHandler", new SendFullHttpResponseHandler(networkContext));

        pipeline.addLast("ProtobufServerHandler", new HttpProtobufRequestHandler(networkHandler, networkContext));
    }

}

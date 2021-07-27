/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License, version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package ws.common.network.client.http.initializer;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.timeout.IdleStateHandler;
import ws.common.network.client.http.handler.HttpProtobufResponseHandler;
import ws.common.network.client.http.handler.SendFullHttpRequestHandler;
import ws.common.network.client.tcp.handler.BuildPackageUseForServerHandler;
import ws.common.network.client.tcp.handler.ParsePackageFromServerHandler;
import ws.common.network.server.config.MessageFrameConfig;
import ws.common.network.server.config.interfaces.ConnConfig;
import ws.common.network.server.handler.tcp.IdleDisconnectedHandler;
import ws.common.network.server.handler.tcp.IdleOfflineHandler;
import ws.common.network.server.interfaces.NetworkContext;
import ws.common.network.server.interfaces.NetworkHandler;

import java.net.URI;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {
    private NetworkContext networkContext;
    private URI uri;
    private String host;

    private ChannelPipeline pipeline;

    public HttpClientInitializer(NetworkContext networkContext, URI uri, String host) {
        this.networkContext = networkContext;
        this.uri = uri;
        this.host = host;
    }


    public HttpClientInitializer(ChannelPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpClientCodec());
        p.addLast(new HttpContentDecompressor());
        p.addLast(new HttpObjectAggregator(1048576));

        p.addLast("SendFullHttpResponseHandler", new SendFullHttpRequestHandler(networkContext, uri, host));

        p.addLast(new HttpProtobufResponseHandler(networkContext));
    }

    public void initHttpChannel(ConnConfig connConfig, NetworkHandler networkHandler, NetworkContext networkContext) throws Exception {
        /**
         * 客户端连接异常断开处理[readerIdleTime 服务器一定时间内未接受到客户端消息, writerIdleTime 服务器一定时间内向客户端发送消息, allIdleTime 同时没有读写的时间]
         */
        pipeline.addLast(new IdleStateHandler(connConfig.getOfflineTimeout(), 0, 0));
        pipeline.addLast(new IdleOfflineHandler(networkHandler));
        pipeline.addLast(new IdleStateHandler(connConfig.getDisconnTimeout(), 0, 0));
        pipeline.addLast(new IdleDisconnectedHandler(networkHandler));
        pipeline.addLast(new BuildPackageUseForServerHandler(networkContext)); // OUT -> 组装发送的报文
        pipeline.addLast(new LengthFieldBasedFrameDecoder(
                // 默认为大端处理
                MessageFrameConfig.CLIENT_RECEIVE_MESSAGE_PACKAGE_RECEIVED_MAX_LEN, // 消息的最大长度
                MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM, // HDR1(1) 消息帧的占位符字节数
                MessageFrameConfig.MESSAGE_PACKAGE_TOTAL_BYTE_NUM, // Length(2) 整个消息帧的长度的值所占的字节数（当前short）
                -(MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM + MessageFrameConfig.MESSAGE_PACKAGE_TOTAL_BYTE_NUM), // -(HDR1(1) + Length(2))
                (MessageFrameConfig.MESSAGE_PACKAGE_PLACEHOLDER_BYTE_NUM + MessageFrameConfig.MESSAGE_PACKAGE_TOTAL_BYTE_NUM), // (HDR1(1) + Length(2))
                true));
        pipeline.addLast(new ParsePackageFromServerHandler(networkHandler, networkContext));

    }

}

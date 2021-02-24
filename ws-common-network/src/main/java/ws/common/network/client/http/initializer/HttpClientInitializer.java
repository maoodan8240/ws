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

import java.net.URI;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import ws.common.network.client.http.handler.HttpProtobufResponseHandler;
import ws.common.network.client.http.handler.SendFullHttpRequestHandler;
import ws.common.network.server.interfaces.NetworkContext;

public class HttpClientInitializer extends ChannelInitializer<SocketChannel> {
    private NetworkContext networkContext;
    private URI uri;
    private String host;

    public HttpClientInitializer(NetworkContext networkContext, URI uri, String host) {
        this.networkContext = networkContext;
        this.uri = uri;
        this.host = host;
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
}

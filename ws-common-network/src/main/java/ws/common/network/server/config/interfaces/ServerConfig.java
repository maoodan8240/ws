package ws.common.network.server.config.interfaces;

import java.util.List;

public interface ServerConfig {

    ConnConfig getConnConfig();

    List<ChannelConfig<?>> getChannelConfigs();
}
package games.omg.regularproxy;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(
  id = "regularproxy",
  name = "regular.proxy",
  version = "1.0.0",
  url = "https://regular.place",
  description = "a regular proxy",
  authors = {"OMG"},
  dependencies = {
    @Dependency(id = "protocolize")
  }
)

public class Main {

  private final ProxyServer server;
  private final Logger logger;

  @Inject
  public Main(ProxyServer server, Logger logger) {
    this.server = server;
    this.logger = logger;

    logger.info("regular proxy running");
  }

  @Subscribe
  public void onInitialize(ProxyInitializeEvent event) {
    server.getEventManager().register(this, new Authorize());
  }
}

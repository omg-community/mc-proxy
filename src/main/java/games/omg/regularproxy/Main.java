package games.omg.regularproxy;

import org.slf4j.Logger;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerPing;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

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
    // Protocolize.protocolRegistration().createPacket(
    //   RespawnPacket.class,
    //   Protocol.PLAY,
    //   PacketDirection.CLIENTBOUND,
    //   0x45 // not sure if this is meant to be 0x45 or simply 45 but neither work
    // );

    server.getEventManager().register(this, new Authorize());
  }

  // @Subscribe
  // public void onLoginEvent(LoginEvent event) {
  //   Player player = event.getPlayer();

  //   event.setResult(ComponentResult.denied(
  //     Component.text("goodbye " + player.getUsername())
  //             .color(NamedTextColor.GOLD)
  //             .decorate(TextDecoration.BOLD)
  //   ));
  // }

  @Subscribe
  public void onPing(ProxyPingEvent event) {
    ServerPing originalPing = event.getPing();
    ServerPing.Builder pingBuilder = originalPing.asBuilder();

    // Make a bold orange MOTD
    // Component motd = Component.text("regular.place");
    // motd = motd.style(Style.style(TextColor.color(0xFFA500), TextDecoration.BOLD));
    // // Add more text on the next line without styles and colors and formatting
    // motd = motd.append(Component.newline());
    // motd = motd.append(Component.text("a regular proxy"));

    Component part1 = Component.text("regular.place")
                                .color(NamedTextColor.GOLD)
                                .decorate(TextDecoration.BOLD);

    Component part2 = Component.text("\ndays without incident: 0");

    Component motd = Component.text().append(part1, part2).build();

    pingBuilder.description(motd);

    // Update the ping
    event.setPing(pingBuilder.build());
  }
}

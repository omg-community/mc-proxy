package games.omg.regularproxy;

import dev.simplix.protocolize.api.Location;
import dev.simplix.protocolize.api.Protocolize;
import dev.simplix.protocolize.data.packets.PlayerPosition;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Plugin implements Listener {
  @EventHandler
  public void onServerConnect(ServerConnectEvent event) {
    ProxiedPlayer player = event.getPlayer();
    
    sendToVoid(player);
  }

  public void sendToVoid(ProxiedPlayer player) {
    PlayerPosition playerPositionPacket = new PlayerPosition(
      new Location(0, 0, 0, 0, 0),
      true
    );

    Protocolize.playerProvider().player(player.getUniqueId()).sendPacket(playerPositionPacket);
  }
}
